package very.util.consul.api

import very.util.consul.entity.{KVStoreGetRequest, KVStoreResponse}

private[consul] trait KVStoreApi extends ConsulApi {
  api =>

  private[consul] trait KVStore {
    private def req(key: S) = api.req(s"kv/$key")

    def read(key: S) = {
      req(key).extract[KVStoreResponse].toOption
    }

    def read(request:KVStoreGetRequest) = {
      ???
    }

    def payload(key: S, body: S) = {
      req(key).put(body).opResult
    }

    def delete(key: S) = {
      req(key).method("DELETE").opResult
    }
  }

  object KVStore extends KVStore
}
