package com.timzaak.oauth.entity

import org.joda.time.{DateTime, DateTimeZone}

case class OAuthClient(
                        id: String,
                        created_at: DateTime = DateTime.now(DateTimeZone.UTC),
                        updated_at: DateTime = DateTime.now(DateTimeZone.UTC),
                        client_id: String,
                        client_secret: String,
                        name: String,
                        status: String,
                        redirect_url: Option[String] = None
) {

}
