package com.timzaak.coin.huobi.api.response

case class ErrorResponse(
  status:S,
  ts:L,
  `error-code`:S,
  `error-msg`:S
)extends Response
