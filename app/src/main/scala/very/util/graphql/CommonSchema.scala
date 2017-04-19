package very.util.graphql

import sangria.schema._


trait CommonSchema {
  def pageType[Context, T](name:String ,ofType: ObjectType[Context, T]) = ObjectType(name, fields[Context, (Vector[T], Long)](
    Field(name = "list", fieldType = ListType(ofType), resolve = _.value._1),
    Field(name = "totalCount", fieldType = LongType, resolve = _.value._2)
  ))
}

object CommonSchema extends CommonSchema