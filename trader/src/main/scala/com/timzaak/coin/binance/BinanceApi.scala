package com.timzaak.coin.binance

import com.timzaak.coin.binance.entity.Sym.Sym

trait BinanceApi {
  def ApiKey: S

  def ApiSecret: S

  def depthUrl(coinMarket: Sym) = s"wss://stream.binance.com:9443/ws/${coinMarket.toString}@depth"

  def KlineUrl(coinMarket: Sym, interval: S = "1m") = s"wss://stream.binance.com:9443/ws/${coinMarket.toString}@kline_${interval}"




}
