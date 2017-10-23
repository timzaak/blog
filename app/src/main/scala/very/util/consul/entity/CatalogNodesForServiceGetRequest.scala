package very.util.consul.entity

case class CatalogNodesForServiceGetRequest(
    service: S,
    dc: S = "",
    tag: S = "",
    near: S = "",
    `node-meta`: S = ""
)
