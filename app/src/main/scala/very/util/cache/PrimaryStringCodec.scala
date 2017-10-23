package very.util.cache

import scalacache.serialization.Codec

trait PrimaryStringCodec {

  implicit object StringToString extends Codec[String, String] {
    def serialize(value: String): String  = value
    def deserialize(data: String): String = data
  }

  implicit object BooleanToString extends Codec[Boolean, String] {
    def serialize(value: Boolean): String  = value.toString
    def deserialize(data: String): Boolean = data.toBoolean
  }

  //TODO: add other primary type
}
