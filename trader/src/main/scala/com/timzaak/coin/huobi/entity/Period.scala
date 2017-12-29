package com.timzaak.coin.huobi.entity

object Period extends Enumeration {
  type Period = Value
  val `1min`, `5min`, `15min`, `30min`, `60min`, `1day`, `1mon`, `1week`, `1year` = Value
}
