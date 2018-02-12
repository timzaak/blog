package com.timzaak.coin.huobi.api.http.response

import com.timzaak.coin.huobi.api.common.entity.Trade

case class TradeResponse(
  id:L,
  ts:L,
  tick:Trade
)
