package com.timzaak.coin.binance

import com.timzaak.coin.binance.entity.CoinMarket.CoinMarket

trait BinanceApi {
  def ApiKey: S

  def ApiSecret: S

  def depthUrl(coinMarket: CoinMarket) = s"wss://stream.binance.com:9443/ws/${coinMarket.toString}@depth"

  def KlineUrl(coinMarket: CoinMarket, interval: S = "1m") = s"wss://stream.binance.com:9443/ws/${coinMarket.toString}@kline_${interval}"




}
