package com.timzaak.coin.huobi.api.response

case class TradeResposne(
  id:L,
  ts:L,
  tick:Trade
)
case class Trade(
  id:L,
  ts:L,
  data:List[TradeDetail]
)
case class TradeDetail(
  id:L,
  price:D,
  amount:D,
  direction: S,
  ts:L
)
