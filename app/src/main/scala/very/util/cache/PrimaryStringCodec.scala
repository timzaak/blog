package very.util.cache

import scalacache.serialization.Codec

trait PrimaryStringCodec {

  implicit object StringToString extends Codec[String, String]{
    def serialize(value: String): String = value
    def deserialize(data: String): String = data
  }

  //TODO: add other primary type
}