package com.timzaak.coin.binance.request

import com.timzaak.coin.binance.entity.Interval
import com.timzaak.coin.binance.entity.Sym.Sym


case class KlinesRequest(
  symbol: Sym,
  interval: Interval.Interval,
  limit: Int = 500,
  startTime: Option[L] = None,
  endTime: Option[L] = None

)
