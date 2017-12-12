package very.util.conduit

import org.json4s.JsonAST.JObject
import very.util.http.Http4SRequestWrapper
import ws.very.util.json.JsonHelperWithDoubleMode

import scalaj.http.Http

trait ConduitApi extends JsonHelperWithDoubleMode with Http4SRequestWrapper {
  protected def canduitHost: S

  protected def token: S

  def req(route: String, param: JObject = JObject()) = {
    val paramString = toJson(("param"-> param) ~ ("__conduit__"-> ("token"-> token)) ~ ("output"-> "json"))
    Http(s"${canduitHost}/$route").postData(paramString).asString
  }

  def ping = {
    Http(s"${canduitHost}/conduit.ping").asString
  }
}
