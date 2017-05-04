package com.timzaak.schema

import com.timzaak.di.ActionDI

import scala.concurrent.Future

object AuthException extends Exception {
  override def getMessage: String = "认证失败"
}

case class GraphQLContext(userIdOpt: O[UserId], di: ActionDI) {
  def withAuth[T](func: UserId => Future[T]): Future[T] = {
    userIdOpt match {
      case Some(userId) => func(userId)
      case _ => AuthException
    }
  }
}
