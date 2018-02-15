package com.timzaak.api.binance.api.http.request

import com.timzaak.api.binance.entity.Interval


case class KlinesRequest(
  symbol: S,
  interval: Interval.Interval,
  limit: Int = 500,
  startTime: Option[L] = None,
  endTime: Option[L] = None

)
