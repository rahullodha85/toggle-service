package unit.helpers

import metrics.{StatsDClient, StatsDProtocol}
import org.scalatest.mock.MockitoSugar
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}
import play.api.Configuration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class StatsDClientSpec extends WordSpec
    with Matchers
    with PropertyChecks
    with MockitoSugar {

  def createTestStatsDClient() = {
    val configuration = Configuration.from(Map("statsd.port" -> 1, "statsd.server" -> "", "statsd.metric-host" -> "", "statsd.metric-namespace" -> ""))
    new StatsDClient(configuration)
  }

  val statsdClient = createTestStatsDClient()

  "StatsDProtocol" should {
    "format a string according to the StatsD protocol" in {
      forAll("key", "value", "metric", "sampleRate") { (key: String, value: String, metric: String, sampleRate: Double) =>
        val stat = key + ":" + value + "|" + metric + (if (sampleRate < 1) "|@" + sampleRate else "")
        StatsDProtocol.stat(key, value, metric, sampleRate) should equal(stat)
      }
    }

    "time a future" in {
      statsdClient.timeTaken(0, Future {
        Thread.sleep(50); 6
      }).map { time =>
        assert(time >= 50)
      }
    }

    "time something synchronous" in {
      statsdClient.timeTaken(0, {
        val a = 10; a
      }).map { time =>
        assert(time < 50)
      }
    }
  }
}
