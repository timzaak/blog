package very.util.consul.api

trait StatusApi extends ConsulApi{ api=>
  trait Status {
    private def req(key: S) = api.req(s"status/$key")

    def getLeader =
      req("leader").stringResult

    def getPeers =
      req("peers").extract[List[String]]
  }


  object Status extends Status
}
