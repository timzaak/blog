package very.util.consul.entity

case class KVStoreGetRequest(
  key:O[S] = None,
  dc:O[S] = None,
  recurse:O[B] = None,
  raw:O[B] = None,
  keys:O[B] = None,
  separator:O[S] = None
)
