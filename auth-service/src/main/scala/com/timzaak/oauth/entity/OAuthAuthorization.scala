package com.timzaak.oauth.entity

import org.joda.time.{DateTime, DateTimeZone}

trait OAuthAuthorization{
  def authorization:String
  def code:String
  def client_id:String
  def user_id: Int
  def created_at:DateTime
  def deleted_at:DateTime
}
case class DefaultOAuthAuthorization (
  authorization:String,
  code:String,
  client_id: String,
  user_id: Int,
  created_at: DateTime = DateTime.now(DateTimeZone.UTC),
  deleted_at: DateTime = DateTime.now(DateTimeZone.UTC).plusMinutes(30)
) extends OAuthAuthorization

object OAuthAuthorization{

  def apply(
             authorization:String,
             code:String,
             client_id: String,
             user_id: Int,
             created_at: DateTime = DateTime.now(DateTimeZone.UTC),
             deleted_at: DateTime = DateTime.now(DateTimeZone.UTC).plusMinutes(30)
           ):OAuthAuthorization =
    DefaultOAuthAuthorization(authorization,code,client_id,user_id,created_at,deleted_at)
}
