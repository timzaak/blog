package com.timzaak.entity
import com.timzaak.action.UserAccAction
import com.timzaak.di.ActionDI
import com.timzaak.schema.GraphQLContext
import sangria.schema._
import sangria.macros.derive._
trait UserSchema {
  private val userAccountInputType = deriveInputObjectType[UserAccount](
    InputObjectTypeDescription("the user"),
    DocumentInputField("id","user id")
  )

  //acc: S, pwd: S
  private implicit val acc = Argument("acc",StringType)
  private implicit val pwd = Argument("pwd",StringType)


  private val mutationType = deriveObjectType[GraphQLContext,UserAccAction](
    IncludeMethods("login")
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
