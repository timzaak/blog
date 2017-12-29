package com.timzaak.coin.huobi.response

case class ErrorResponse(
  status:S,
  ts:L,
  `error-code`:S,
  `error-msg`:S
)extends Response
