package very.util.consul.entity

case class KVStoreResponse(
    CreatedIndex: Int,
    ModifyIndex: Int,
    LocalIndex: Int,
    Key: String,
    Flags: Int,
    Value: String,
    Session: String
)
