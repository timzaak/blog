package very.util.consul.entity

case class Service(
  Service:S,
  Tags:List[S] = List.empty,
  Address:S,
  Port:I,
  ID:S=""
)
