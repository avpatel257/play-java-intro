import play.api.GlobalSettings
import play.api.libs.json.Json
import play.api.mvc.{RequestHeader, WithFilters}

import scala.concurrent.Future

object Global extends WithFilters(AccessLogFilter) with GlobalSettings {
  override def onError(request: RequestHeader, ex: Throwable) = {
    Future.successful(play.core.j.JavaResults.InternalServerError(Json.toJson(ex.getMessage)))
  }

}
