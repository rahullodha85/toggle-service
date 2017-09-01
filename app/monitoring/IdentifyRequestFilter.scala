package monitoring

import java.util.UUID

import monitoring.Constants._
import org.slf4j.MDC
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.Future

object IdentifyRequestFilter extends Filter {
  def apply(next: RequestHeader => Future[Result])(req: RequestHeader): Future[Result] = {
    MDC.put(CORRELATION_ID, req.headers.get(CORRELATION_ID).getOrElse(UUID.randomUUID().toString))
    next(req)
  }
}

