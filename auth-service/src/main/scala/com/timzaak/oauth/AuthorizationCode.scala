package com.timzaak.oauth

import com.timzaak.oauth.entity.{OAuthAuthorization, OAuthClient, OAuthToken}
import com.timzaak.oauth.error.OAuthException
import com.timzaak.oauth.error.OAuthException._
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}


case class CodeRequest(
                        client_id: S,
                        user_id: I,
                        state: O[S],
                        redirect_url: O[S] = None,
                        scope: Option[S] = None
                      ) extends AuthorizeRequest {
  override def response_type: S = "code"
}

case class AuthorizationCodeRequest(
                                     code: S,
                                     redirect_uri: S,
                                     client_id: S
                                   ) extends TokenRequest{
  override def grant_type:S = "authorization_code"
}

trait AuthorizationCode {
  implicit def ec: ExecutionContext

  def getOAuthClientByClientId(client_id: S): Future[O[OAuthClient]]

  def createCode(user_id: I, client: OAuthClient): Future[OAuthAuthorization]

  def getAuthorizationCode(client_id: S, code: S): Future[O[OAuthAuthorization]]

  def createToken(client_id: S, user_id: I): Future[OAuthToken]

  def authorize(req: CodeRequest): Future[Either[OAuthException, OAuthAuthorization]] = {
    import req._
    getOAuthClientByClientId(client_id).flatMap {
      case Some(client) =>
        createCode(user_id, client).map(Right(_))
      case None =>
        Future.successful(Left(ClientError))
    }
  }

  def token(req: AuthorizationCodeRequest) = {
    import req._
    getAuthorizationCode(client_id, code).flatMap {
      case Some(auth) =>
        getOAuthClientByClientId(client_id).flatMap {
          case Some(client) =>
            if (!client.redirect_url.contains(redirect_uri)) {
              Future.successful(Left(RedirectUriError))
            } else if (auth.deleted_at.isAfter(DateTime.now)) {
              Future.successful(Left(CodeError))
            } else {
              createToken(client_id, auth.user_id).map(Right(_))
            }
          case None =>
            Future.successful(Left(ClientError))
        }
      case None =>
        Future.successful(Left(CodeError))
    }
  }

}
