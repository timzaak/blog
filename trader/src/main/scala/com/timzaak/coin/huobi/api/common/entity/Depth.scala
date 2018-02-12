package com.timzaak.coin.huobi.api.common.entity

import org.json4s.JValue
import org.json4s.JsonAST.JArray
import ws.very.util.json.JsonHelperWithDoubleMode

case class Depth(
                  ts: L,
                  bids: List[(D, D)],
                  asks: List[(D, D)]
                )

object Depth extends JsonHelperWithDoubleMode {

  import JsonExtractors._

  val Array(ts, bids, asks) = Array("ts", "bids", "asks").map(param _)
  val tuple2Array = ((arr: JValue) => {

    Some(arr.extract[JArray].values.map {
      case (price: D) :: (amount: Double) :: Nil =>
        price -> amount
    })
  }).toCase

  def unapply(json: JValue) = json match {
    case ts(int(_ts)) & bids(tuple2Array(_bids)) & asks(tuple2Array(_asks)) =>
      Some(Depth(_ts.toLong, _bids, _asks))
  }
}
