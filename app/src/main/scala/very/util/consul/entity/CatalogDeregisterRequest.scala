package very.util.consul.entity

case class CatalogDeregisterRequest(
  Node:S,
  Datacenter:S = "",
  CheckID:S = "",
  ServiceId:S =""
)