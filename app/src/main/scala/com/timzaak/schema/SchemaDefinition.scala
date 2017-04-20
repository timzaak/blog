package com.timzaak.schema

import sangria.schema._

trait SchemaDefinition extends UserSchema with CommentSchema {

  val Query = ObjectType(
    name = "Query",
    fields = commentSchemaQuery
  )

  val mutationType = ObjectType(
    name = "Mutation",
    fields = userSchemaMutation ::: commentSchemaMutation
  )

  val graphQLSchema = Schema(Query, Some(mutationType))
}