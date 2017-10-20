package very.util.consul.api

import org.json4s.Formats
import org.json4s.native.Serialization.write
import ws.very.util.json.JsonHelperWithDoubleMode

import scalaj.http.{Http, HttpRequest, HttpResponse}

private[consul] trait ConsulApi extends JsonHelperWithDoubleMode {

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

  implicit class HttpRequestWrapper(request: HttpRequest) {

    def extract[T](implicit formats: Formats, mf: scala.reflect.Manifest[T]) = {
      stringResult.right.map(parseJson(_).extract[T])
    }

    private def getCCParams(cc: Product) = {
      val values = cc.productIterator
      cc.getClass.getDeclaredFields.map(_.getName -> values.next)
    }

    // TODO: 危险方法， 不支持 case class 复杂数据结构
    def params[T<:Product](p:T) = {
      request.params(getCCParams(p).map{case (key,value)=>
        (key , value.toString)
      }.filter(_._2 == ""))
    }
    def opResult:Boolean = {
      request.asString.is2xx
    }

    def putData[T<: AnyRef](data:T) ={
      request.put(write(data))
    }

    def stringResult:Either[HttpResponse[String],String] = {
      val resp = request.asString
      if (resp.is2xx) {
        resp.body
      } else {
        resp
      }
    }
  }

}
