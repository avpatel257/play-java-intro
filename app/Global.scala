import play.api.GlobalSettings
import play.api.mvc.WithFilters

object Global extends WithFilters(AccessLogFilter) with GlobalSettings {

}
