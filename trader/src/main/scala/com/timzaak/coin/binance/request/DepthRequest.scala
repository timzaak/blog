package com.timzaak.coin.binance.request

import com.timzaak.coin.binance.entity.Sym.Sym

case class DepthRequest(
  symbol: Sym,
  limit:I = 100
)
