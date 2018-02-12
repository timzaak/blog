package com.timzaak.coin.huobi.api.http.entity

object OrderStatus extends Enumeration {
  type OrderStatus = Value
  //pre-submitted 准备提交, submitting , submitted 已提交, partial-filled 部分成交, partial-canceled 部分成交撤销, filled 完全成交, canceled 已撤销
  val `pre-submitted`,
  submitting,
  submitted,
  `partial-filled`,
  `partial-canceled`,
  filled,
  canceled = Value

}
