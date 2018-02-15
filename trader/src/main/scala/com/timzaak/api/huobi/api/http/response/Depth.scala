package com.timzaak.api.huobi.api.http.response

import com.timzaak.api.huobi.entity.Depth
import org.json4s.JValue
import ws.very.util.json.JsonHelperWithDoubleMode

case class DepthResponse(
                          status: S,
                          ch: S,
                          ts: L,
                          tick: Depth
                        )

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
