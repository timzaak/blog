package com.timzaak.api.binance.api


trait BinanceWSApi{

  protected val wsHost = s"wss://stream.binance.com:9443/ws"

  protected def depthUrl(symbol: S) = s"${wsHost}/${symbol}@depth"

  protected def klineUrl(symbol: S, interval: S = "1m") = s"${wsHost}/${symbol}@kline_${interval}"

  protected def tradeStreamsUrl(symbol:S) = s"${wsHost}/${symbol}@trade"

  protected def aggregateTradeStreamsUrl(symbol:S) = s"${wsHost}/${symbol}@aggTrade"

}
