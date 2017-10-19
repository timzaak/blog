package com.timzaak.schema

import com.timzaak.di.ActionDI
import very.util.security.{Access, PermissionCheckable}

import scala.concurrent.{ExecutionContext, Future}

object AuthException extends Exception {
  override def getMessage: String = "????"
}

case class GraphQLContext(userIdOpt: O[UserId], di: ActionDI) {

  def withAuth[T](func: UserId => Future[T]): Future[T] = {
    userIdOpt match {
      case Some(userId) => func(userId)
      case _ => AuthException
    }
  }

  def withAuth[T](func: UserId => T): T = {
    userIdOpt match {
      case Some(userId) => func(userId)
      case _ => throw AuthException
    }
  }

  def withUserPermission[T](p: PermissionCheckable, access: Access = Access.Nothing)(func: UserId => T)(implicit ec: ExecutionContext) = {
    withAuth { userId: UserId =>

      di.accessAction.withUserAccess(userId, p, access).map { passed =>
        if (passed) {
          func(userId)
        } else {
          AuthException
        }
      }
    }
  }

}
