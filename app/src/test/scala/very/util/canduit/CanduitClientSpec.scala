package very.util.canduit

import org.json4s.JsonAST.JObject
import org.scalatest.{FreeSpec, Matchers}
import ws.very.util.json.JsonHelperWithDoubleMode
import ws.very.util.security.SHA


class CanduitClientSpec extends FreeSpec with Matchers with JsonHelperWithDoubleMode {
  val host = "https://test-ufab5xzeitt5.phacility.com/api"
  val client = new CanduitApi {
    override protected val canduitHost: S = host

    override protected val token: S = "api-ekicvzzxzp6vzuotl7m5iuuiqggk"

  }

  "canduit client" - {
    "can authenticate" in {
      val authToken = now.sec
      val authSignature = SHA(authToken, SHA.SHA_1)
      //      val result = client.req("conduit.connect",
      //        ("user" -> "timzaak") ~
      //          ("host" -> host) ~
      //          ("client" -> "canduit") ~
      //          ("authToken" -> authToken) ~
      //          ("authSignature" -> authSignature)
      //      ).asString
      //      println(result)
    }
    "can ping" in {
      assert(client.ping.is2xx, "ping does not work")
    }
  }

}
