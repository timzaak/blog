package very.util.consul.entity

case class KVStoreGetRequest(
  key:S = "",
  dc:S = "",
  recurse:B = false,
  raw: B = false,
  keys:B = false,
  separator:S = "/"
)

