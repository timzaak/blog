package com.timzaak.oauth.entity

import org.joda.time.{DateTime, DateTimeZone}

case class OAuthToken(
                       id: String,
                       created_at: DateTime,
                       deleted_at: DateTime = DateTime.now(DateTimeZone.UTC).plusDays(1),
                       user_id: Long,
                       client_id: String,
                       access_token: String,
                       refresh_token: String,
                       token_type:String,
                       scope: Option[String] = None
                     ) {

}

