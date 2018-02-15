package com.timzaak.api.huobi.api.http.entity

object OrderType extends Enumeration{
  type OrderType = Value

  //buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖
  val `buy-market`, `sell-market`,`buy-limit`, `sell-limit` = Value
}
