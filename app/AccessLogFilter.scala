
import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext


object AccessLogFilter extends Filter {
  val logger = play.Logger.of("accesslog")

  def apply(nextFilter: RequestHeader => Future[Result])
           (req: RequestHeader): Future[Result] = {

    val startTime = System.currentTimeMillis
    nextFilter(req).map { result =>
      val endTime = System.currentTimeMillis
      val requestTime = endTime - startTime
      val msg = s"${req.remoteAddress} ${req.method} ${req.uri} ${req.version} ${result.header.status} ${requestTime} ${req.headers.get("X-User-ID").getOrElse("-")} ${req.headers.get("X-Session-ID").getOrElse("-")} ${req.headers.get("user-agent").getOrElse("-")}"
      logger.info(msg)
      result.withHeaders("Request-Time" -> requestTime.toString)
    }
  }
}