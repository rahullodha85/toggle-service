package controllers

import constants.Constants
import helpers.ControllerPayloadLike._
import helpers.ControllerTimeoutLike._
import helpers.{ConfigurationProvider, ControllerPayloadLike}
import models._
import play.api.Play.current
import play.api._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import repos.{MongoToggleCountsRepo, ToggleUsageRepo}
import services.ToggleUsageService

import scala.concurrent.Future

trait ToggleRepo {
  def getAll(): Future[Seq[Toggle]]
  def get(name: String): Option[Toggle]
  def create(toggle: ToggleUpdate): Future[Boolean]
  def update(toggle: ToggleUpdate): Future[Boolean]
  def delete(toggleName: String): Future[Boolean]
}

trait ToggleHistoryService {
  def getAllHistory(): Future[Seq[ToggleHistory]]
  def getHistory(toggleName: String): Future[Seq[ToggleHistory]]
  def create(t: Toggle, action: String): Future[Boolean]
}

class ToggleCtlr(repo: ToggleRepo, historySvc: ToggleHistoryService, toggleUsagesRepo: ToggleUsageRepo, configuration: Configuration)
    extends Controller
    with ToggleSerialization
    with ToggleHistorySerialization {

  val toggleUsagesService: ToggleUsageService = new ToggleUsageService(toggleUsagesRepo, repo, MongoToggleCountsRepo, configuration)

  // wtf, should I really have to do this???
  def toWhiteSpace(pathParam: String): String = pathParam.replaceAll("%20", " ")

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/toggles

    DESCRIPTION
      Get toggles, optionally filtering by a given criterium

    PARAMETERS
      filter: Enum(unused) String(query, optional) <- Optional filter criterium, currently supporting only 'unused' (default '')

    RESULT
      Array Toggle

    Toggle: models.ToggleForSwagger
      toggle_name: String
      toggle_state: Boolean
      created_timestamp: String
      modified_timestamp: String
      description: String
      toggle_type: String
      modified_by: String
                                         """)
  def getToggles(filter: String = "") = Action.async { implicit request =>
    //GET /toggles?filter=unused
    withTimeout {
      filter match {
        case "unused" => toggleUsagesService.findUnusedToggles().map(writeResponseGet(_))
        case _        => repo.getAll().map(writeResponseGet(_))
      }
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET /toggle-service/toggles/from/{host}

    DESCRIPTION
      Copy the toggles from another environment, then return them

    PARAMETERS
      host: String <- host part of url you want to migrate toggles from

    RESULT
      Array Toggle
                                         """)
  def copyFrom(host: String) = Action.async { implicit request =>
    // GET /toggle-service/toggles/from/*host
    // just call it with host, we'll assume port 9880 since that is what is currently used.
    // by all means add this to config or some such...
    //println("called: " + url)
    //val oldurl = "http://hd1dtgl01lx.saksdirect.com:9880/toggle-service/toggles?details=1"
    val url = s"http://${host}:9860/v1/toggle-service/toggles"

    WS.url(url).get().flatMap { response =>
      if (response.status == 200)
        ApiModel.resultsAs[Seq[ToggleUpdate]](response.body) match {
          //(Json.parse(response.body) \ "response" \ "results").validate[Seq[ToggleUpdate]] match { // for the old toggleservice...  I'll leave it here for now
          case JsSuccess(x, _) =>
            Future.traverse(x) { t =>
              repo.create(t)
            }.flatMap { _ => repo.getAll().map(writeResponseGet(_)) }
          case JsError(e) =>
            Logger.error("couldn't parse response: " + response.body)
            throw new JsResultException(e)
        }
      else {
        val msg = s"call to $url failed: statusCode: ${response.status}, body: ${response.body}"
        Logger.error(msg)
        throw new Exception(msg)
      }
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    PUT /toggle-service/toggles

    DESCRIPTION
      Create/replace entire database of toggles via a big dump of json in form of list of ToggleUpdate models

    RESULT
      Boolean
                                         """)
  def createAll() = Action.async { implicit request =>
    // PUT /toggles
    withTimeout {

      if (isCreateAllowed) {
        // delete existing stuff
        repo.getAll().flatMap { toggles =>
          Future.traverse(toggles) { toggle =>
            repo.delete(toggle.toggle_name.toUpperCase)
          }
        }
        // some json under key items ...
        Future.traverse(getRequestItems[ToggleUpdate]) { up =>
          repo.delete(up.toggle_name.toUpperCase).flatMap { _ => repo.create(up) }
        }.map { _ => writeResponseGet(true) }

      } else {
        Future { writeResponseError(Seq(ApiErrorModel(s"toggles creation is not allowed on ${currentEnv} environment", "CreateAllForbidden")), ControllerPayloadLike.Status(403)) }
      }

    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    POST /toggle-service/toggles

    DESCRIPTION
      Update multiple toggles via a big dump of json in form of list of ToggleUpdate models

    RESULT
      Boolean
                                         """)
  def updateAll() = Action.async { implicit request =>
    // POST /toggle-service/toggles
    withTimeout {
      Future.traverse(getRequestItems[ToggleUpdate]) { up =>
        repo.update(up)
      }.map { _ => writeResponseGet(true) }
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/toggles/{toggleName}

    DESCRIPTION
      Fetch the list of country and currency info we have in the DB

    PARAMETERS
      toggleName: String <- the name of the toggle you want

    RESULT
      Toggle
                                         """)
  def getByName(toggleName: String) = Action.async { implicit request =>
    // GET /toggles/toggleName
    withTimeout {
      Future {
        repo.get(toWhiteSpace(toggleName))
      }.map(writeResponseGet(_))
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    PUT /toggle-service/toggles/{toggleName}

    DESCRIPTION
      Create a toggle via json in form of ToggleUpdate model

    PARAMETERS
      toggleName: String <- name of toggle

    RESULT
      Boolean
                                         """)
  def create(toggleName: String) = Action.async { implicit request =>
    // PUT /toggles/tname
    withTimeout {
      if (isCreateAllowed) {
        repo.create(getRequestItem[ToggleUpdate]).map(writeResponseStore(_))
      } else {
        Future { writeResponseError(Seq(ApiErrorModel(s"toggles creation is not allowed on ${currentEnv} environment", "CreateForbidden")), ControllerPayloadLike.Status(403)) }
      }
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    POST /toggle-service/toggles/{toggleName}

    DESCRIPTION
      Update a toggle via json in form of ToggleUpdate model

    PARAMETERS
      toggleName: String <- name of toggle

    RESULT
      Boolean
                                         """)
  def update(toggleName: String) = Action.async { implicit request =>
    // POST /toggles/toggleName
    withTimeout {
      repo.update(getRequestItem[ToggleUpdate]).map(writeResponseUpdate(_))
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    DELETE /toggle-service/toggles/{toggleName}

    DESCRIPTION
      Delete a toggle

    PARAMETERS
      toggleName: String <- name of toggle

    RESULT
      Boolean
                                         """)
  def delete(toggleName: String) = Action.async { implicit request =>
    // DELETE /toggles/name
    withTimeout {
      if (isDeleteAllowed) {
        repo.delete(toWhiteSpace(toggleName)).map(writeResponseRemove(_))
      } else {
        Future { writeResponseError(Seq(ApiErrorModel(s"toggles deletion is not allowed on ${currentEnv} environment", "DeleteForbidden")), ControllerPayloadLike.Status(403)) }
      }
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/history

    DESCRIPTION
      Fetch all the toggles

    RESULT
      Array ToggleHistory

    ToggleHistory: models.ToggleHistoryForSwagger
      toggle_name: String
      toggle_state: Boolean
      modified_timestamp: String
      description: String
      toggle_type: String
      modified_by: String
      action: String
                                         """)
  def getAllHistory() = Action.async { implicit request =>
    withTimeout {
      historySvc.getAllHistory().map(writeResponseGet(_))
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/toggles/{toggleName}/history

    DESCRIPTION
      Fetch all the toggles

    PARAMETERS
      toggleName: String <- the name of the toggle

    RESULT
      Array ToggleHistory
                                         """)
  def getHistory(toggleName: String) = Action.async { implicit request =>
    withTimeout {
      historySvc.getHistory(toWhiteSpace(toggleName)).map(writeResponseGet(_))
    }
  }

  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    DELETE  /toggle-service/toggles

    DESCRIPTION
      Delete and return the toggles that match given filter param

    PARAMETERS
      filter: Enum(unused) String(query) <- filter criterium, currently supporting only 'unused'

    RESULT
      Array Toggle

                                         """)
  def deleteToggles(filter: String = "") = Action.async { implicit request =>
    //DELETE /toggles?filter=unused
    filter match {
      case "unused" => withTimeout {
        toggleUsagesService.deleteUnusedToggles().map(writeResponseGet(_))
      }
      case _ => Future { writeResponseError(Seq(ApiErrorModel(s"Invalid filter. Currently only unused toggles can be deleted, use ?filter=unused", "InvalidFilter")), ControllerPayloadLike.Status(400)) }
    }
  }

  private def currentEnv: String = {
    configuration.getString(Constants.HBC_ENV).get
  }

  private def isCreateAllowed: Boolean = {
    isCreateDeleteAllowed
  }

  private def isDeleteAllowed: Boolean = {
    isCreateDeleteAllowed
  }

  private def isCreateDeleteAllowed: Boolean = {
    val envsDisallowingCreateDelete = configuration.getString(Constants.ENVS_DISALLOWING_CREATE_DELETE).getOrElse(currentEnv).toLowerCase.split(",")
    !envsDisallowingCreateDelete.contains(currentEnv.toLowerCase)
  }
}

object ToggleController extends ToggleCtlr(repos.MongoTogglesRepo, repos.MongoTogglesHistoryRepo, repos.MongoToggleUsageRepo, ConfigurationProvider.getConfiguration)
