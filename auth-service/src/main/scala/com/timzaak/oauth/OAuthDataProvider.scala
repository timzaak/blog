package com.timzaak.oauth

import com.timzaak.oauth.entity.{OAuthAuthorization, OAuthClient, OAuthToken, OAuthUser}
import com.timzaak.oauth.error.OAuthException

import scala.concurrent.Future

trait OAuthDataProvider {
  def getUser(username: S, password: S): Future[O[OAuthUser]]

  def createToken(client_id: S, user_id: I): Future[OAuthToken]

  def getOAuthClientByClientId(client_id: S): Future[O[OAuthClient]]

  def createCode(user_id: I, client: OAuthClient): Future[OAuthAuthorization]

  def getAuthorizationCode(client_id: S, code: S): Future[O[OAuthAuthorization]]

  // check and refresh. promise atomic
  def refreshToken(token:String,refreshToken:String):Future[Either[OAuthException,OAuthToken]]
}
