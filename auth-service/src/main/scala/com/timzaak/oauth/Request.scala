package com.timzaak.oauth

trait AuthorizeRequest{
  def response_type:S
}

trait TokenRequest {
  def grant_type:S
}
