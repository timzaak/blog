package very.util.consul
import org.scalatest.{ FreeSpec, Matchers }
import very.util.consul.entity.{ CatalogDeregisterRequest, CatalogRegisterRequest, Service }

class ConsulClientTest extends FreeSpec with Matchers {
  val client = new ConsulHttpClient {
    override protected def consulHost: S = "http://127.0.0.1:8500"
  }

  "can get status" in {
    assert(client.Status.getLeader.isRight)
  }

  "agent" - {
    "can get members" in {
      assert(client.Agent.members.isRight)
    }
  }

  "can register and deregister service" in {
    val service = Service(Service = "hello_test",
                          Tags = List("abc", "bbb"),
                          Address = "localhost",
                          Port = 3000)
    val registerResult = client.Catalog.register(
      CatalogRegisterRequest(
        Node = "node",
        Address = "localhost",
        Service = Some(service)
      )
    )
    assert(registerResult)
    assert(client.Catalog.deregister(CatalogDeregisterRequest("node")))
  }
}
