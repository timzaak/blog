package com.timzaak.oauth

import com.timzaak.oauth.entity.OAuthClient
import com.timzaak.oauth.error.OAuthException
import com.timzaak.oauth.error.OAuthException._

import scala.concurrent.{ExecutionContext, Future}

case class ImplicitRequest(
                          client_id: S,
                          redirect_uri: O[S],
                          scope: O[S] = None,
                          state: O[S] = None
                        ) extends AuthorizeRequest {
  override val response_type = "token"
}

trait ImplicitAuthorization {

  implicit def ec: ExecutionContext
  def EnableRedirectAnyUrl: Boolean
  def provider:OAuthDataProvider

  def authorize(req: ImplicitRequest): Future[Either[OAuthException, OAuthClient]] = {
    import req._
    provider.getOAuthClientByClientId(client_id).map {
      case Some(client) =>
        val redirectUri = redirect_uri.getOrElse(client.redirect_url)
        if(!EnableRedirectAnyUrl && client.redirect_url != redirectUri) {
          Left(RedirectUriError)
        } else {
          Right(client)
        }
      case _ =>
        Left(ClientError)
    }
  }
}
