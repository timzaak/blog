package com.timzaak.oauth.error

trait OAuthException {
  def message: String

  def code: Int
}

object OAuthException {
  def apply(message: String, code: Int) = DefaultOAuthException(message, code)
}

case class DefaultOAuthException(message: String, code: Int) extends OAuthException
