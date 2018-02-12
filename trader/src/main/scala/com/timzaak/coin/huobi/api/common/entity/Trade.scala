package com.timzaak.coin.huobi.api.common.entity

case class Trade(
                  id:L,
                  ts:L,
                  data:List[TradeDetail]
                )
case class TradeDetail(
                        id:L,
                        price:D,
                        amount:D,
                        direction: S,
                        ts:L
                      )
