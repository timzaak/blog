package com.timzaak.api.binance.api.http.request

case class DepthRequest(
  symbol: S,
  limit:I = 100
)
