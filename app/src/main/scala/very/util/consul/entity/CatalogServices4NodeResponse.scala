package very.util.consul.entity

case class CatalogServices4NodeResponse(
    Node: Node,
    Services: Map[String, Service]
)
