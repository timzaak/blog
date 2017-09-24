package very.util.consul

import org.json4s.Formats
import ws.very.util.json.JsonHelperWithDoubleMode

import scalaj.http.{Http, HttpRequest}

private[consul] trait ConsulApi extends JsonHelperWithDoubleMode {

  def consulHost: S

  def apiVersion = "v1"

  lazy val urlPrefix = s"$consulHost/$apiVersion"

  trait HttpConfiguration {
    def connTimeoutMs: Int

    def readTimeoutMs: Int
  }

  trait DefaultHttpConfiguration extends HttpConfiguration {
    val connTimeoutMs: Int = 2000

    val readTimeoutMs: Int = 5000
  }

  object DefaultHttpConfiguration extends DefaultHttpConfiguration

  lazy val httpConfig: HttpConfiguration = DefaultHttpConfiguration

  def req(apiUrl: S) = {
    Http(s"$urlPrefix/$apiUrl").timeout(httpConfig.connTimeoutMs, httpConfig.readTimeoutMs)
  }

  implicit class HttpRequestWrapper(request: HttpRequest) {

    def extract[T](implicit formats: Formats, mf: scala.reflect.Manifest[A]) = {
      val resp = request.asString
      if (resp.is2xx) {
        Right(parseJson(request.asString.body).extract[T])
      } else {
        Left(resp)
      }
    }

    def opResult = {
      val resp = request.asString
      resp.is2xx
    }
  }

}
