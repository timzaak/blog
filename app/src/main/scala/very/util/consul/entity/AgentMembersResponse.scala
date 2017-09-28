package very.util.consul.entity

case class AgentMembersResponse(
  Name:S,
  Addr:S,
  Port:I,
  Tags:Map[String,String] = Map.empty,
  Status: I,
  ProtocolMin:I,
  ProtocolMax:I,
  ProtocolCur:I,
  DelegateMin:I,
  DelegateMax:I,
  DelegateCur:I
)
