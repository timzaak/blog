package very.util.consul.entity

case class CatalogRegisterRequest(
  ID:S = "",
  Node:S ,
  Address:S,
  DataCenter:S ="",
  TaggedAddresses:Map[S,S] =Map.empty,
  NodeMeta:Map[S,S]=Map.empty,
  Service:Option[Service] = None,
  Check:Option[Check] = None
)


