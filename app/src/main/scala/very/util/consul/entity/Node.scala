package very.util.consul.entity

case class Node(
    ID: S,
    Node: S,
    Address: S,
    Datacenter: S,
    TaggedAddresses: Map[String, String],
    Meta: Map[String, String]
)
