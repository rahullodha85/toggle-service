package controllers

import helpers.ConfigurationProvider
import helpers.ControllerPayloadLike._
import helpers.ControllerTimeoutLike._
import models.ToggleSerialization
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}
import services.ToggleUsageService

class ToggleCountsController(toggleUsagesService: ToggleUsageService)
    extends Controller with ToggleSerialization {

  // GET /counts
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/counts

    DESCRIPTION
      Get all counts of TOGGLES for the last days

    PARAMETERS
      max_days: Int(query, optional) <- retrieve max days from now (default 90). use max_days=0 to retrieve all

    RESULT
      Array ToggleCounts

    ToggleCounts: models.ToggleCounts
      date:         String
      banner:       String
      env:          String
      used_active:  Integer
      unused:       Integer
      used_expired: Integer                                         """)
  def getCounts(maxDays: Int) = Action.async { implicit request =>
    withTimeout {
      toggleUsagesService.findCounts(maxDays).map(writeResponseGet(_))
    }
  }

  // GET counts/now
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    GET  /toggle-service/counts/now

    DESCRIPTION
      Get the current counts of Toggles

    RESULT
      ToggleCounts                                     """)
  def currentCounts() = Action.async { implicit request =>
    withTimeout {
      toggleUsagesService.getCurrentCounts().map(writeResponseStore(_))
    }
  }

  // POST counts/now
  @no.samordnaopptak.apidoc.ApiDoc(doc = """
    POST  /toggle-service/counts/now

    DESCRIPTION
      Record the current counts of Toggles

    RESULT
      ToggleCounts                                     """)
  def recordCurrentCounts() = Action.async { implicit request =>
    withTimeout {
      toggleUsagesService.recordCurrentCounts().map(writeResponseStore(_))
    }
  }

}

object ToggleCountsController extends ToggleCountsController(ToggleUsageService)