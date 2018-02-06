package com.timzaak.oauth

import com.timzaak.oauth.entity.{OAuthAuthorization, OAuthClient, OAuthToken}
import com.timzaak.oauth.error.OAuthException
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}


case class CodeRequest(
                        client_id: S,
                        user_id: I,
                        state: O[S],
                        redirect_url: O[S] = None,
                        scope: Option[S] = None
                      ) extends Request {
  override def response_type: S = "code"
}

case class AuthorizationCodeRequest(
                                     code: S,
                                     redirect_uri: S,
                                     client_id: S
                                   )

case class CodeResponse(
                         code: S,
                         expiration: I
                       ) extends Response


trait AuthorizationCode {
  implicit def ec: ExecutionContext

  def getOAuthClientByClientId(client_id: S): Future[O[OAuthClient]]

  def createCode(user_id: I, client: OAuthClient): Future[OAuthAuthorization]

  def getAuthorizationCode(client_id: S, code: S): Future[O[OAuthAuthorization]]

  def createToken(client_id: S, user_id: I): Future[OAuthToken]

  def handleCode(req: CodeRequest): Future[Either[OAuthException, OAuthAuthorization]] = {
    import req._
    getOAuthClientByClientId(client_id).flatMap {
      case Some(client) =>
        createCode(user_id, client).map(Right(_))
      case None =>
        Future.successful(Left(OAuthException("不存在Client", 100)))
    }
  }

  def handleAuthorizationCode(req: AuthorizationCodeRequest) = {
    import req._
    getAuthorizationCode(client_id, code).flatMap {
      case Some(auth) =>
        getOAuthClientByClientId(client_id).flatMap {
          case Some(client) =>
            if (!client.redirect_url.contains(redirect_uri)) {
              Future.successful(Left(OAuthException("回调 redirect_uri 错误", 102)))
            } else if (auth.deleted_at.isAfter(DateTime.now)) {
              Future.successful(Left(OAuthException("Code 过期", 103)))
            } else {
              createToken(client_id, auth.user_id).map(Right(_))
            }
          case None =>
            Future.successful(Left(OAuthException("不存在Client", 100)))
        }
      case None =>
        Future.successful(Left(OAuthException("不存在 Code", 101)))
    }
  }

}
