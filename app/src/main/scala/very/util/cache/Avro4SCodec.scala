package very.util.cache

import java.io.ByteArrayOutputStream

import com.sksamuel.avro4s._
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream

import scalacache.serialization.Codec

class Avro4SCodec[T](implicit schemaFor: SchemaFor[T],
                     fromRecord: FromRecord[T], toRecord: ToRecord[T]
                    ) extends Codec[T, String] {
  override def serialize(value: T): String = {
    val baos = new ByteArrayOutputStream()
    val aos = AvroJsonOutputStream[T](baos)(schemaFor, toRecord)
    aos.write(value)
    aos.close()

    val result = baos.toString("UTF-8")
    baos.close()
    result
  }

  override def deserialize(data: String): T = {
    val in = new ByteInputStream(data.getBytes("UTF-8"), data.size)
    val input = AvroJsonInputStream[T](in)(schemaFor, fromRecord)
    val result = input.singleEntity
    input.close()
    in.close()
    result.getOrElse(throw new Exception(s"serialise fail, data: $data"))
  }
}
