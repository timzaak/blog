package com.timzaak.coin.huobi.response

case class KlineResponse(
  status:S,
  ch:S,
  data:List[Kline],
  ts:L
)

case class Kline(
  id:L,
  amount: D,
  count:I,
  open:D,
  close:D,
  low:D,
  high:D,
  vol:D
)
