package very.util.consul.entity

case class CatalogNodesGetRequest(
    dc: S = "",
    near: S = "",
    `node-meta`: S = ""
)
