package com.timzaak.coin.huobi.api.http.response

case class ErrorResponse(
  status:S,
  ts:L,
  `error-code`:S,
  `error-msg`:S
)extends Response
