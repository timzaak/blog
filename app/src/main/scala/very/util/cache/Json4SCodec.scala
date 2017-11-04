package very.util.cache

import ws.very.util.json.JsonHelperWithDoubleMode

import scalacache.serialization.Codec

class Json4SCodec[T <: AnyRef: Manifest] extends Codec[T, String] with JsonHelperWithDoubleMode {
  import org.json4s.native.Serialization.{ read, write }
  def serialize(value: T): String = {
    write(value)(formats)

  }
  def deserialize(data: String): T = read[T](data)
}
