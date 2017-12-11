package very.util.canduit

import org.json4s.JsonAST.JObject
import very.util.http.Http4SRequestWrapper
import ws.very.util.json.JsonHelperWithDoubleMode

import scalaj.http.Http

trait CanduitApi extends JsonHelperWithDoubleMode with Http4SRequestWrapper {
  protected def canduitHost: S

  protected def token: S

  protected var session: Option[String] = None

  def req(route: String, param: JObject) = {
    val paramString = toJson(param ~ ("__conduit__"-> session.getOrElse(token)))
    println(paramString)
    Http(s"${canduitHost}/$route").postData(paramString)
  }

  def ping = {
    Http(s"${canduitHost}/conduit.ping").asString
  }
}
