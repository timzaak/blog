package com.timzaak.coin.huobi.api.websocket.response

import com.timzaak.coin.huobi.api.common.entity.{Depth, Kline, Trade}
import org.json4s.JValue
import org.json4s.JsonAST.{JInt, JString}
import ws.very.util.json.JsonHelperWithDoubleMode


case class ChResponse[T](ch:S, ts:L, tick:T)


object ChResponse extends JsonHelperWithDoubleMode{
  def extract(json:JValue):ChResponse[_] = {
    val JInt(ts) = json \ "ts"
    val JString(sub) = json \ "sub"
    val tick = json \"tick"
    if(sub.contains("kline")){
      ChResponse(sub, ts.toLong, tick.extract[Kline])
    }else if(sub.contains("depth")){
      ChResponse(sub, ts.toLong,Depth.unapply(tick).get)
    }else if(sub.contains("trade.detail")){
      ChResponse(sub, ts.toLong, tick.extract[Trade])
    }else {
      ChResponse(sub, ts.toLong, tick.extract[Kline])
    }
  }
}