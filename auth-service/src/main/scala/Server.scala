import play.api._
import play.api.ApplicationLoader.Context
import play.filters.HttpFiltersComponents

class Server extends ApplicationLoader {
  def load(context: Context) = {
    (new BuiltInComponentsFromContext(context) with HttpFiltersComponents with Routes).application
  }
}

