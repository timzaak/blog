package com.timzaak.oauth.entity

import org.joda.time.{DateTime, DateTimeZone}

/*
 * promise one user one client has only record
 */
trait OAuthToken {
  def id: String

  def created_at: DateTime

  def deleted_at: DateTime

  def user_id: Int

  def client_id: String

  def access_token: String

  def refresh_token: String

  def token_type: String

  def scope: Option[String]


}


case class DefaultOAuthToken(
                              id: String,
                              user_id: Int,
                              client_id: String,
                              access_token: String,
                              refresh_token: String,
                              token_type: String,
                              created_at: DateTime,
                              deleted_at: DateTime = DateTime.now(DateTimeZone.UTC).plusDays(1),
                              scope: Option[String] = None
                            ) extends OAuthToken {

}

object OAuthToken {
  def apply(
             id: String,
             user_id: Int,
             client_id: String,
             access_token: String,
             refresh_token: String,
             token_type: String,
             created_at: DateTime,
             deleted_at: DateTime = DateTime.now(DateTimeZone.UTC).plusDays(1),
             scope: Option[String] = None): OAuthToken =
    DefaultOAuthToken(id, user_id, client_id, access_token, refresh_token, token_type, created_at, deleted_at, scope)
}

