package com.timzaak.entity
import com.timzaak.action.UserAccAction
import com.timzaak.schema.GraphQLContext
import sangria.schema._
import sangria.macros.derive._
trait UserSchema {
  private val userAccountInputType = deriveInputObjectType[UserAccount](
    InputObjectTypeDescription("the user"),
    DocumentInputField("id","user id")
  )

  private val mutationType = deriveObjectType[GraphQLContext,UserAccAction](
    IncludeMethods(
      "login",
      "sampleRegister"
    )
  )

  val userSchemaMutation = fields[GraphQLContext, U](
    Field(
      name = "user",
      fieldType = mutationType,
      description = Some("api of user module"),
      resolve = (c => c.ctx.di.userAccAction): Context[GraphQLContext, U] ⇒ Action[GraphQLContext, UserAccAction]
    )
  )
}
