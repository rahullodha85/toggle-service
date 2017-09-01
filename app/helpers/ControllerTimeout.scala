package helpers

import akka.pattern._
import constants.Constants
import globals.Contexts
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.libs.Akka

import scala.concurrent._
import scala.concurrent.duration._

class ControllerTimeoutLike(actionTimeout: Int) {

  // call these with some arbitrary blocking code
  def timeout[T](body: => T): Future[T] =
    timingoutFuture(actionTimeout, Future(body))

  def timeout[T](time: Long)(body: => T): Future[T] =
    timingoutFuture(time, Future(body))

  // call these if you already have a future
  def withTimeout[T](f: Future[T]): Future[T] =
    timingoutFuture(actionTimeout, f)

  def withTimeout[T](time: Long)(f: Future[T]): Future[T] =
    timingoutFuture(time, f)

  private def timingoutFuture[T](time: Long, f: Future[T]): Future[T] = {
    val timeoutFuture = after(time.millis, using = Akka.system.scheduler)(Future.failed(new TimeoutException(Constants.TIMEOUT_MSG)))
    Future.firstCompletedOf(Seq(f, timeoutFuture))(Contexts.ctx)
  }
}

object ControllerTimeoutLike extends ControllerTimeoutLike(ConfigurationProvider.getConfiguration.getInt("controllers.timeout").get)
