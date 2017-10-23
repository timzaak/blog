package very.util.consul.api

import very.util.consul.entity.AgentMembersResponse

trait AgentApi extends ConsulApi { api =>

  private[consul] trait Agent {
    private def req(key: S) = api.req(s"agent/$key")

    def members = {
      req("members").extract[List[AgentMembersResponse]]
    }
  }

  object Agent extends Agent
}
