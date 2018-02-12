package com.timzaak.coin.huobi.api.http.response

import com.timzaak.coin.huobi.api.common.entity.Kline

case class KlineResponse(
  status:S,
  ch:S,
  data:List[Kline],
  ts:L
)extends Response
