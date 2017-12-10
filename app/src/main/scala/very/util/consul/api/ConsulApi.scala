package very.util.consul.api


import org.json4s.Formats
import org.json4s.native.Serialization.write
import ws.very.util.json.JsonHelperWithDoubleMode
import very.util.http.Http4SRequestWrapper
import scalaj.http.{Http, HttpRequest, HttpResponse}

private[consul] trait ConsulApi extends JsonHelperWithDoubleMode with Http4SRequestWrapper{

  protected def consulHost: S

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
}
