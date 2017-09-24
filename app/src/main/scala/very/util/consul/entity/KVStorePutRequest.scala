package very.util.consul.entity

case class KVStorePutRequest(
  key:O[S]=None,
  dc:O[S] =None,
  flags:O[Int] =None,
  cas:O[Int] =None,
  acquire:O[S] = None,
  release:O[S] =None
)
