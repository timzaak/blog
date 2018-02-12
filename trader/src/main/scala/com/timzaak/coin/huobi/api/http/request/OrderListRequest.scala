package com.timzaak.coin.huobi.api.http.request

import com.timzaak.coin.huobi.api.http.entity.OrderStatus.OrderStatus
import com.timzaak.coin.huobi.api.http.entity.OrderType.OrderType

case class OrderListRequest(
  symbol:S,
  states	:List[OrderStatus],
  types:List[OrderType]= List.empty,
  `start-date`:O[S]=None,
  `end-date`:O[S]=None,
  from:Option[S]=None,
  direct:S = "prev",//"next"
  size:O[S] =None
){
  assert(states.size>0,"states should has one")
}
