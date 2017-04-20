package very.util.graphql

import org.json4s.JValue
import sangria.marshalling.FromInput
import sangria.schema._
import ws.very.util.json.JsonHelperWithDoubleMode


trait CommonSchema extends JsonHelperWithDoubleMode {
  def pageType[Context, T](name: String, ofType: ObjectType[Context, T]) = ObjectType(name, fields[Context, (Vector[T], Long)](
    Field(name = "list", fieldType = ListType(ofType), resolve = _.value._1),
    Field(name = "totalCount", fieldType = LongType, resolve = _.value._2)
  ))

  import sangria.marshalling.json4s.native._


  implicit def json4sNativeFromInput[T: Manifest]: FromInput[T] =
    new FromInput[T] {
      val marshaller = Json4sNativeResultMarshaller

      def fromResult(node: marshaller.Node) = node.asInstanceOf[JValue].extract[T]
    }
}

object CommonSchema extends CommonSchema