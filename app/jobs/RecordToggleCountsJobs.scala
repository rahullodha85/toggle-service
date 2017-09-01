package jobs

import akka.actor.Actor
import models.ToggleCounts
import play.Logger
import services.ToggleUsageService

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object InstructionMessage extends Enumeration {
  val Record = Value
}

class RecordToggleCountsJob(toggleUsageService: ToggleUsageService) extends Actor {

  import play.api.libs.concurrent.Execution.Implicits._

  def receive = {

    case InstructionMessage.Record =>
      Try {
        toggleUsageService.recordCurrentCounts()
      } match {
        case Success(result: Future[ToggleCounts]) =>
          result.map(counts => Logger.info(s"${counts} recorded!"))

        case Failure(e: Throwable) =>
          logAndScheduleARetry(e, 60)
      }
  }

  private def logAndScheduleARetry(e: Throwable, delaySeconds: Long): Unit = {
    Logger.error(s"Failed to record count, Retrying in ${delaySeconds} seconds ${e.getMessage}", e)
    context.system.scheduler.scheduleOnce(delaySeconds seconds, self, InstructionMessage.Record)
  }
}
