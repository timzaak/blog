package com.timzaak.api.huobi.entity

case class Kline(
                  id: L,
                  amount: D,
                  count: I,
                  open: D,
                  close: D,
                  low: D,
                  high: D,
                  vol: D
                )
