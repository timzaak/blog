package com.timzaak.oauth

import com.timzaak.oauth.entity.OAuthToken
import com.timzaak.oauth.error.OAuthException

import scala.concurrent.{ExecutionContext, Future}


case  class RefreshTokenRequest(
  refresh_token:String,
  token:String,
  scope: Option[String] = None
) extends TokenRequest{
  override def grant_type = "refresh_token"
}
trait RefreshToken {
  implicit def ec :ExecutionContext
  val provider:OAuthDataProvider

  def token(req:RefreshTokenRequest):Future[Either[OAuthException,OAuthToken]] = {
    provider.refreshToken(req.token,req.refresh_token)
  }

}
