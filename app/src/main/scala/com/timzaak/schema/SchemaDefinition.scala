package com.timzaak.schema

import com.timzaak.entity.UserAuth
import sangria.execution.deferred.{Fetcher, HasId}
import sangria.schema._
import scala.concurrent.Future

trait SchemaDefinition {


  val Query = ObjectType(
    "Query", fields[UserAuth, Unit](
      Field("test", StringType,
        Some("nihao"),
        resolve = _ => ""
      )
    )
  )

  val accArg = Argument("acc", StringType)
  val pwdArg = Argument("pwd", StringType)

  val Mutation = ObjectType("Mutation", fields[UserAuth, Unit](
    Field("getToken", OptionType(StringType),
      arguments = accArg :: pwdArg :: Nil,
      resolve = ctx => "12332")
  ))

  val graphQLSchema = Schema(Query, Some(Mutation))
}
