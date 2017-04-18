package com.timzaak.schema

import com.timzaak.entity.UserSchema
import sangria.schema._

trait SchemaDefinition extends UserSchema{


  val Query = ObjectType(
    "Query", fields[GraphQLContext, Unit](
      Field("test", StringType,
        Some("nihao"),
        resolve = _ => ""

      )
    )
  )

  val mutationType = ObjectType(
    name = "Mutation",
    fields = userSchemaMutation
  )


  val graphQLSchema = Schema(Query, Some(mutationType))
}

//object SchemaDefinition extends SchemaDefinition