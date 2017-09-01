package globals

import akka.actor.Props
import com.novus.salat.global.{ctx => SalatGlobalContext}
import filters.ServiceFilters
import helpers.ControllerPayload
import jobs.{InstructionMessage, RecordToggleCountsJob}
import monitoring.IdentifyRequestFilter
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.mvc._
import play.api.{Application, GlobalSettings, Play}
import repos._
import services.ToggleUsageService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

class GlobalServiceSettings extends GlobalSettings with ControllerPayload {

  override def doFilter(next: EssentialAction): EssentialAction =
    Filters(
      super.doFilter(next),
      IdentifyRequestFilter,
      ServiceFilters.TimingFilter,
      ServiceFilters.IncrementFilter,
      ServiceFilters.ExceptionFilter
    )

  override def onError(request: RequestHeader, ex: Throwable): Future[Result] =
    Future.successful(InternalServerError("This shouldn't happen"))

  override def onStart(app: Application) {
    SalatGlobalContext.clearAllGraters()
    SalatGlobalContext.registerClassLoader(Play.classloader(Play.current))
    ServiceRepo.createServiceRepoClient
    scheduleRecordToggleCountsJob
  }

  override def onStop(app: Application) = {
    ServiceRepo.destroyServiceRepoClient
  }

  private def scheduleRecordToggleCountsJob = {
    val recordCountsJobActor = Akka.system.actorOf(Props(new RecordToggleCountsJob(ToggleUsageService)))
    Akka.system.scheduler schedule (
      initialDelay = 1.second,
      interval = 1.hour,
      recordCountsJobActor,
      InstructionMessage.Record
    )
  }

}

