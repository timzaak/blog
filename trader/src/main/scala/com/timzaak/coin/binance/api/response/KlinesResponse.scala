package com.timzaak.coin.binance.api.response

import org.json4s.JsonAST._

case class KlinesResponse(
                           openTime: L,
                           open: S,
                           high: S,
                           low: S,
                           close: S,
                           volume: S,
                           closeTime: L,
                           quoteAssetVolume: S,
                           tradesNum: I,
                           takerBaseVolume: S,
                           takerQuoteVolume: S
                         )

object KlinesResponse {

  def parse(json: JValue): List[KlinesResponse] = {
    json match {
      case JArray(arr) =>
        arr.map {
          case JArray(values) =>
            val JInt(openTime) = values(0)
            val JString(open) = values(1)
            val JString(high) = values(2)
            val JString(low) = values(3)
            val JString(close) = values(4)
            val JString(volume) = values(5)
            val JInt(closeTime) = values(6)
            val JString(quoteAssetVolume) = values(7)
            val JInt(tradesNum) = values(8)
            val JString(takerBaseVolume) = values(9)
            val JString(takerQuoteVolume) = values(10)
            KlinesResponse(
              openTime.toLong,
              open,
              high,
              low,
              close, volume, closeTime.toLong, quoteAssetVolume, tradesNum.toInt, takerBaseVolume, takerQuoteVolume
            )
        }
    }
  }
}
