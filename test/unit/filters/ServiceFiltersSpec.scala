package unit.filters

import helpers.ControllerTimeoutLike._
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}
import play.api.Play
import play.api.http.HttpVerbs.GET
import play.api.libs.iteratee.Iteratee
import play.api.libs.json.Json
import play.api.mvc.Results._
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test.{FakeApplication, FakeRequest}
import utils.TestGlobal

import scala.concurrent._
import scala.concurrent.duration._
import scala.language.postfixOps

class FiltersSpec
    extends WordSpec
    with Matchers
    with BeforeAndAfterAll {

  val actionTimeoutMillis = 5500

  val testRouter: PartialFunction[(String, String), Handler] = {
    case (GET, "/slowRequest") =>
      Action.async {
        implicit request =>
          timeout {
            Thread.sleep(actionTimeoutMillis * 3)
            Ok("Should never get here")
          }
      }
    case (GET, "/errorRequest") =>
      Action {
        throw new NullPointerException("Bad code!")
        Ok("Should never get here")
      }
  }

  override def beforeAll() = {
    Play.start(FakeApplication(withGlobal = Some(TestGlobal), withRoutes = testRouter,
      additionalConfiguration = Map(
        "controllers.timeout" -> actionTimeoutMillis
      )))
  }

  override def afterAll() = {
    Play.stop()
  }

  "ServiceFilters" should {

    "return TimeoutException after configured time" in {
      val result: Result = Await.result(route(FakeRequest(GET, "/slowRequest")).get, (actionTimeoutMillis * 2) millis)
      val bytesContent = Await.result(result.body |>>> Iteratee.consume[Array[Byte]](), Duration.Inf)
      val contentAsJson = Json.parse(new String(bytesContent))
      result.header.status shouldBe 503
      ((contentAsJson \ "errors")(0) \ "error").as[String] == "TimeoutException" shouldBe true
    }

    "handle exception when it's thrown by controller" in {
      val result = route(FakeRequest(GET, "/errorRequest")).get
      ((contentAsJson(result) \ "errors")(0) \ "error").as[String] == "NullPointerException" shouldBe true
    }
  }
}
