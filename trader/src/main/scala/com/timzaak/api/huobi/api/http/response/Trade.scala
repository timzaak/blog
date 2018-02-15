package com.timzaak.api.huobi.api.http.response

import com.timzaak.api.huobi.entity.Trade

case class TradeResponse(
  id:L,
  ts:L,
  tick:Trade
)
