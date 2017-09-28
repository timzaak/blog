package very.util.consul.entity

case class KVStorePutRequest(
  key:S="",
  dc:S ="",
  flags:I = 0,
  cas:I = 0,
  acquire:S= "",
  release:S = ""
)
