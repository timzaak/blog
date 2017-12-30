package com.timzaak.coin.binance.api.request

import com.timzaak.coin.binance.api.entity.Interval


case class KlinesRequest(
  symbol: S,
  interval: Interval.Interval,
  limit: Int = 500,
  startTime: Option[L] = None,
  endTime: Option[L] = None

)
