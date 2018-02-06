package com.timzaak.oauth.entity

import org.joda.time.{DateTime, DateTimeZone}

case class OAuthAuthorization (
  authorization:String,
  code:String,
  client_id: String,
  user_id: Int,
  created_at: DateTime = DateTime.now(DateTimeZone.UTC),
  deleted_at: DateTime = DateTime.now(DateTimeZone.UTC).plusMinutes(30)
)
