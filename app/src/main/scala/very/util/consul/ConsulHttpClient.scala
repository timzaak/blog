package very.util.consul

import very.util.consul.api._

trait ConsulHttpClient extends KVStoreApi with CatalogApi with StatusApi with AgentApi{

}
