package com.timzaak.coin.binance.request

case class DepthRequest(
  symbol: S,
  limit:I = 100
)
