package monitoring

import java.util.UUID

import monitoring.Constants._
import org.slf4j.MDC
import play.api.Play.current
import play.api.libs.ws.{WS, WSClient, WSRequestHolder}

class CorrelationIdWSClient(wsOpt: Option[WSClient] = None) extends WSClient {
  lazy val ws = wsOpt getOrElse WS.client

  override def underlying[T]: T = ws.underlying
  override def url(url: String): WSRequestHolder = ws.url(url) withHeaders getCorrelationID

  def getCorrelationID = CORRELATION_ID -> {
    val cID = Option(MDC.get(CORRELATION_ID))
    cID.getOrElse {
      val newCID = UUID.randomUUID().toString
      MDC.put(CORRELATION_ID, newCID)
      newCID
    }
  }
}

object CorrelationIdWSClient extends CorrelationIdWSClient(None)
