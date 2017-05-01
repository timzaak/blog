package com.timzaak.schema

import com.timzaak.di.ActionDI

object AuthException extends Exception

case class GraphQLContext(userIdOpt: O[UserId], di: ActionDI) {
  def withAuth[T](func: UserId => T): T = {
    userIdOpt match {
      case Some(userId) => func(userId)
      case _ => throw AuthException
    }
  }
}
