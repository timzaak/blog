package com.timzaak.oauth

import com.timzaak.oauth.entity.{OAuthToken, OAuthUser}
import com.timzaak.oauth.error.OAuthException
import com.timzaak.oauth.error.OAuthException._
import scala.concurrent.{ExecutionContext, Future}

case class PasswordRequest(
                            client_id: S,
                            username: S,
                            password: S,
                            scope: Option[S] = None
                          ) extends TokenRequest {
  override def grant_type: S = "password"
}

trait PasswordAuthorization {
  implicit def ec: ExecutionContext

  def getUser(username: S, password: S): Future[O[OAuthUser]]

  def createToken(client_id: S, user_id: I): Future[OAuthToken]

  def token(req: PasswordRequest):Future[Either[OAuthException,OAuthToken]] = {
    import req._
    getUser(username, password).flatMap {
      case Some(user) =>
        createToken(client_id, user.id).map(Right(_))
      case None =>
        Future.successful(Left(InvalidUserOrPassword))
    }
  }
}
