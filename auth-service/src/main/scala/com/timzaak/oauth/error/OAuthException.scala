package com.timzaak.oauth.error

trait OAuthException extends Throwable{
  def message: String

  def code: Int
}

case class DefaultOAuthException(message: String, code: Int) extends OAuthException

object OAuthException {
  def apply(message: String, code: Int) = DefaultOAuthException(message, code)

  val ClientError = OAuthException("不存在 Client", 100)

  val CodeError = OAuthException("Code 错误", 101)

  val RedirectUriError = OAuthException("回调 redirect_uri 错误", 102)

  val InvalidUserOrPassword = OAuthException("错误账号或密码", 103)

  val InvalidToken = OAuthException("token 或 refresh_token 错误", 104)



}


