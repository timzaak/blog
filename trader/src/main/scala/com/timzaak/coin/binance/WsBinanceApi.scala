package com.timzaak.coin.binance

trait WsBinanceApi extends BinanceApi{

  protected val wsHost = s"wss://stream.binance.com:9443/ws"

  protected def depthUrl(symbol: S) = s"wss://${wsHost}/${symbol}@depth"

  protected def klineUrl(symbol: S, interval: S = "1m") = s"wss://${wsHost}/${symbol}@kline_${interval}"


}
