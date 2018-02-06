package com.timzaak.oauth

import com.timzaak.oauth.entity.OAuthClient
import com.timzaak.oauth.error.OAuthException
import com.timzaak.oauth.error.OAuthException._

import scala.concurrent.{ExecutionContext, Future}

case class SimpleRequest(
                          client_id: S,
                          redirect_uri: S,
                          scope: O[S] = None,
                          state: O[S] = None
                        ) extends AuthorizeRequest {
  override val response_type = "token"
}

trait SimpleAuthorization {

  implicit def ec: ExecutionContext

  def getOAuthClientByClientId(client_id: S): Future[O[OAuthClient]]

  def authorize(req: SimpleRequest): Future[Either[OAuthException, OAuthClient]] = {
    import req._
    getOAuthClientByClientId(client_id).map {
      case Some(client) =>
        if (!client.redirect_url.contains(redirect_uri)) {
          Left(RedirectUriError)
        } else {
          Right(client)
        }
      case _ =>
        Left(ClientError)
    }
  }
}
