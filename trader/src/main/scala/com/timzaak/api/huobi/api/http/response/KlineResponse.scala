package com.timzaak.api.huobi.api.http.response

import com.timzaak.api.huobi.entity.Kline

case class KlineResponse(
  status:S,
  ch:S,
  data:List[Kline],
  ts:L
)extends Response
