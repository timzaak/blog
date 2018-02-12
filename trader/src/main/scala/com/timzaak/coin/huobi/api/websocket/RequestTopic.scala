package com.timzaak.coin.huobi.api.websocket

import com.timzaak.coin.huobi.api.http.entity.DepthType.DepthType
import com.timzaak.coin.huobi.api.http.entity.{DepthType, Period}
import com.timzaak.coin.huobi.api.http.entity.Period.Period

object RequestTopic {
  type RequestTopic= S
  def kline(symbol:S,period: Period = Period.`5min`):RequestTopic =
    s"market.$symbol.kline.$period"

  def depth(symbol:S,depthType: DepthType = DepthType.step0):RequestTopic =
    s"market.$symbol.depth.$depthType"

  def tradeDetail(symbol: S):RequestTopic = s"market.$symbol.trade.detail"

  def marketDetail(symbol:S):RequestTopic = s"market.$symbol.detail"
}
