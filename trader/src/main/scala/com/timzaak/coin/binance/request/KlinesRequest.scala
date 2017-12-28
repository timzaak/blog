package com.timzaak.coin.binance.request

import com.timzaak.coin.binance.entity.Interval


case class KlinesRequest(
  symbol: S,
  interval: Interval.Interval,
  limit: Int = 500,
  startTime: Option[L] = None,
  endTime: Option[L] = None

)
