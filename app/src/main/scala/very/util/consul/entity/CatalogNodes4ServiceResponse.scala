package very.util.consul.entity

case class CatalogNodes4ServiceResponse(
  ID:S,
  Node:S,
  Address:S,
  Datacenter:S,
  TaggedAddresses:Map[String,String],
  NodeMeta:Map[String,String],
  CreatedIndex:I,
  ModifyIndex:I,
  ServiceAddress:S,
  ServiceEnableTagOverrider:B,
  ServiceID:S,
  ServiceName:S,
  ServicePort:I,
  ServiceTags:List[I]
)
