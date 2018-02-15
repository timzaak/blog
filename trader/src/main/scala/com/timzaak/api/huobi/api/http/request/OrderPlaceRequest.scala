package com.timzaak.api.huobi.api.http.request

import com.timzaak.api.huobi.api.http.entity.OrderType.OrderType

case class OrderPlaceRequest(
  `account-id`:S,
  symbol:S,
  amount: S,
  `type`: OrderType,
  price: O[S]= None,
  source: O[S]= None
)
