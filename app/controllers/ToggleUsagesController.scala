package controllers

import constants.Constants
import helpers.{ConfigurationProvider, ControllerPayloadLike}
import helpers.ControllerPayloadLike._
import helpers.ControllerTimeoutLike._
import models.{ApiErrorModel, ToggleCounts, ToggleRegistration, ToggleSerialization}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import repos._
import services.ToggleUsageService

import scala.concurrent.Future

class ToggleUsagesController(toggleUsagesRepo: ToggleUsageRepo, toggleCountsRepo: ToggleCountsRepo)
    extends Controller with ToggleSerialization {

  val toggleUsagesService: ToggleUsageService = new ToggleUsageService(toggleUsagesRepo, MongoTogglesRepo, toggleCountsRepo, ConfigurationProvider.getConfiguration)
  // GET /usages
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/usages

    DESCRIPTION
      Get all usages

    RESULT
      Array ToggleUsage

    ToggleUsage: models.ToggleUsage
      toggle_name: String
      user:        String
                                         """)
  def getAllUsages() = Action.async { implicit request =>
    withTimeout {
      toggleUsagesRepo.getAll().map(writeResponseGet(_))
    }
  }

  // GET /usages/{user}
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/usages/{user}

    DESCRIPTION
      Get all usages by a given user

    PARAMETERS
      user: String <- the name of the user to filter for

    RESULT
      Array ToggleUsage
                                         """)
  def getTogglesByUser(user: String) = Action { implicit request =>
    writeResponseGet(toggleUsagesRepo.getByUser(user))
  }

  // GET /toggles/{toggleName}/usages
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/toggles/{toggleName}/usages

    DESCRIPTION
      Get the usages of a given toggle

    PARAMETERS
      toggleName: String <- the name of the toggle to filter for

    RESULT
      Array ToggleUsage
                                         """)
  def getToggleUsages(toggleName: String) = Action.async { implicit request =>
    withTimeout {
      Future {
        toggleUsagesRepo.getByToggleName(toggleName)
      }
    }.map(writeResponseGet(_))
  }

  // POST /registrations
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    POST /toggle-service/registrations

    DESCRIPTION
      Register a list of toggles used by a given user. For each toggle requires a ToggleDefinition in order to create the non-existing toggles

    PARAMETERS
      registration: ToggleRegistration(body)

    ToggleRegistration: models.ToggleRegistration
      user: String
      toggles: Array ToggleDefinition

    ToggleDefinition: models.ToggleDefinition
      toggle_name:  String
      description:  String
      toggle_type:  String
                                         """)
  def register() = Action.async { implicit request =>
    withTimeout {
      toggleUsagesService.register(getRequestItem[ToggleRegistration])
    }.map {
      case Constants.TOGGLE_REGISTRATION_SUCCESS => writeResponseGet("Successfully registered toggles")
      case _                                     => writeResponseError(Seq(ApiErrorModel("Toggle registration failed", "ToggleRegistrationError")), ControllerPayloadLike.Status(500))
    }
  }

}

object ToggleUsagesController extends ToggleUsagesController(MongoToggleUsageRepo, MongoToggleCountsRepo)