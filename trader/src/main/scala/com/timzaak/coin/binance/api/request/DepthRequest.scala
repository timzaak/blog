package com.timzaak.coin.binance.api.request

case class DepthRequest(
  symbol: S,
  limit:I = 100
)
