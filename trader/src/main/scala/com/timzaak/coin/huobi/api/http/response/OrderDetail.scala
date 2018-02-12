package com.timzaak.coin.huobi.api.http.response

import com.timzaak.coin.huobi.api.http.entity.OrderType.OrderType

case class OrderDetail(
  `account-id`:L,
  amount:S,
  `canceled-at`:Option[L],
  createdAt:L,
  `field-amount`:S,
  `field-cash-amount`:S,
  `field-fees`:S,
  `finished-at`:Option[L],
  price:S,
  source:S,
  state:S,
  id:L,
  symbol:S,
  `type`:OrderType
)
