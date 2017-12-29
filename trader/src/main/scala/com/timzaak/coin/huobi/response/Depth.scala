package com.timzaak.coin.huobi.response

import org.json4s.JValue
import org.json4s.JsonAST.JArray
import ws.very.util.json.JsonHelperWithDoubleMode

case class DepthResponse(
                          status: S,
                          ch: S,
                          ts: L,
                          tick: Depth
                        )

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
      println("???")
      Some(Depth(_ts.toLong, _bids, _asks))
  }
}

object DepthResponse extends JsonHelperWithDoubleMode {

  import JsonExtractors._

  val Array(status, ch, ts, tick) = Array("status", "ch", "ts", "tick").map(param _)

  def apply(json: JValue): DepthResponse = {
    json match {
      case status(str(_status)) & ch(str(_ch)) & ts(int(_ts)) & tick(Depth(_tick)) =>
        DepthResponse(_status, _ch, _ts, _tick)

    }
  }
}
