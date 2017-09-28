package very.util.consul.entity

case class Check(
  Node:S,
  CheckID:S,
  Name:S,
  Notes:S,
  Status:S,
  ServiceID:S
)
