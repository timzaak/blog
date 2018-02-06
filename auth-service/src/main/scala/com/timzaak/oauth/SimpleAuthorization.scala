package com.timzaak.oauth

case class SimpleRequest(
  client_id:S,
  redirect_uri:O[S]=None,
  scope:O[S]= None,
  state:O[S]= None
) extends Request{
  val response_type = "token"
}

trait SimpleAuthorization {
  
}
