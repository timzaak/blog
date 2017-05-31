package com.timzaak.schema

import com.timzaak.action.UserAccAction
import com.timzaak.entity.UserAccount
import sangria.macros.derive._
import sangria.schema._

trait UserSchema {
  private val userAccountInputType = deriveInputObjectType[UserAccount](
    InputObjectTypeDescription("the user"),
    DocumentInputField("id", "user id")
  )

  private val mutationType: ObjectType[GraphQLContext, UserAccAction] =
    deriveObjectType[GraphQLContext, UserAccAction](
      ObjectTypeName("UserAccMutationAction"),
      IncludeMethods(
        "register"
      )
    )
  private val queryType: ObjectType[GraphQLContext, UserAccAction] =
    deriveObjectType[GraphQLContext, UserAccAction](
      ObjectTypeName("UserAccQueryAction"),
      IncludeMethods(
        "login",
        /*
         * "getRegisterCaptcha",
         * "checkMobileExists" ??????User??????????????Auth??
         * */
        "getRegisterCaptcha",
        "checkMobileExists"
      )
    )

  val userSchemaQuery = fields[GraphQLContext, U](
    Field(
      name = "user",
      fieldType = queryType,
      resolve = c => c.ctx.di.userAccAction: UserAccAction
    )
  )
  val userSchemaMutation = fields[GraphQLContext, U](
    Field(
      name = "user",
      fieldType = mutationType,
      description = Some("api of user module"),
      resolve = c => c.ctx.di.userAccAction: UserAccAction
    )
  )
}
