package com.timzaak.oauth.entity

import org.joda.time.{DateTime, DateTimeZone}

trait OAuthClient {
  def id: String

  def created_at: DateTime

  def updated_at: DateTime

  def client_id: String

  def client_secret: String

  def name: String

  def status: String

  def redirect_url: String


}

case class DefaultOAuthClient(
                               id: String,
                               client_id: String,
                               client_secret: String,
                               name: String,
                               status: String,
                               redirect_url: String,
                               created_at: DateTime = DateTime.now(DateTimeZone.UTC),
                               updated_at: DateTime = DateTime.now(DateTimeZone.UTC),
                             ) extends OAuthClient

object OAuthClient {
  def apply(
             id: String,
             client_id: String,
             client_secret: String,
             name: String,
             status: String,
             created_at: DateTime = DateTime.now(DateTimeZone.UTC),
             updated_at: DateTime = DateTime.now(DateTimeZone.UTC),
             redirect_url: String
           ): OAuthClient = DefaultOAuthClient(id, client_id, client_secret, name, status, redirect_url, created_at, updated_at)
}
