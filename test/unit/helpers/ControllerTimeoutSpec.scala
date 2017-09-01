package unit.helpers

import helpers._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import play.api.Play
import play.api.test.FakeApplication
import utils.TestGlobal

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class ControllerTimeoutSpec extends WordSpec
    with Matchers
    with BeforeAndAfterAll
    with ControllerPayload {

  val actionTimeoutMillis = 500

  override def beforeAll() = {
    Play.start(FakeApplication(
      withGlobal = Some(TestGlobal),
      additionalConfiguration = Map(
        "controllers.timeout" -> actionTimeoutMillis
      )
    ))
  }

  override def afterAll() = {
    Play.stop()
  }

  "ControllerTimeout" should {
    "return error on timeout" in {
      intercept[TimeoutException](
        Await.result(
          ControllerTimeoutLike.timeout {
            Thread.sleep(actionTimeoutMillis + 1000)
            Ok("Won't get here")
          },
          (2 * actionTimeoutMillis) milliseconds
        )
      )
    }
    "return error on an async timeout" in {
      intercept[TimeoutException](
        Await.result(
          ControllerTimeoutLike.withTimeout {
            Future {
              Thread.sleep(actionTimeoutMillis + 1000)
              Ok("Won't get here")
            }
          },
          (2 * actionTimeoutMillis) milliseconds
        )
      )
    }
  }
}
