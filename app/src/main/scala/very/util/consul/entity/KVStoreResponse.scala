package very.util.consul.entity

case class KVStoreResponse(
  createdIndex: Int,
  modifyIndex:Int,
  localIndex:Int,
  key:String,
  flags:Int,
  value:String,
  session: String
)
