package very.util.conduit

import org.json4s.JsonAST.JObject
import org.scalatest.{FreeSpec, Matchers}
import ws.very.util.json.JsonHelperWithDoubleMode
import ws.very.util.security.SHA


class ConduitClientSpec extends FreeSpec with Matchers with JsonHelperWithDoubleMode {
  val host = "https://test-ufab5xzeitt5.phacility.com/api"
  val client = new ConduitApi {
    override protected val canduitHost: S = host

    override protected val token: S = "api-h5xaygxkizrrdv7y4jthq7aanajo"

  }

  "canduit client" - {
    "can whoami" in {
      assert(client.req("user.whoami").is2xx, "can not ge whao am is")
    }
    "can ping" in {
      assert(client.ping.is2xx, "ping does not work")
    }
  }

}
