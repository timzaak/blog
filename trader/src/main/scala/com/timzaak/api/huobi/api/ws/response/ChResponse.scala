package com.timzaak.api.huobi.api.ws.response


import com.timzaak.api.huobi.entity.{Kline, Trade, Depth}
import org.json4s.JValue
import org.json4s.JsonAST.{JInt, JString}
import ws.very.util.json.JsonHelperWithDoubleMode


case class ChResponse[T](ch:S, ts:L, tick:T)


object ChResponse extends JsonHelperWithDoubleMode{
  def extract(json:JValue):ChResponse[_] = {
    val JInt(ts) = json \ "ts"
    val JString(ch) = json \ "ch"
    val tick = json \"tick"
    if(ch.contains("kline")){
      ChResponse(ch, ts.toLong, tick.extract[Kline])
    }else if(ch.contains("depth")){
      ChResponse(ch, ts.toLong,Depth.unapply(tick).get)
    }else if(ch.contains("trade.detail")){
      ChResponse(ch, ts.toLong, tick.extract[Trade])
    }else {
      ChResponse(ch, ts.toLong, tick.extract[Kline])
    }
  }
}