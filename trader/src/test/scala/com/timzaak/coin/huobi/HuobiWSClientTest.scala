package com.timzaak.coin.huobi

import com.timzaak.coin.huobi.api.websocket.{HuobiWebSocketListener, RequestTopic}
import org.json4s.JValue
import org.scalatest.{FreeSpec, Matchers}
import ws.very.util.json.JsonHelperWithDoubleMode

// sbt "testOnly com.timzaak.coin.huobi.HuobiWSClientTest"
class HuobiWSClientTest extends FreeSpec with Matchers with JsonHelperWithDoubleMode{
  "hello" in {
    val client:HuobiWebSocketClient =
      new HuobiWebSocketClient(new HuobiWebSocketListener {override def onClosed(client: HuobiWebSocketClient, code: _root_.com.timzaak.coin.huobi.api.websocket.I, reason: _root_.com.timzaak.coin.huobi.api.websocket.S): U = ???

        override def onFailure(client: HuobiWebSocketClient, t: Throwable): U = {

        }

        override def onOpen(client: HuobiWebSocketClient): U = {
          //val rt = RequestTopic.depth("btcusdt")
          //val rt = RequestTopic.tradeDetail("btcusdt")
          val rt = RequestTopic.marketDetail("btcusdt")
          client.subscribe(rt)
        }

        override def onMessage(client: HuobiWebSocketClient, message: JValue): U = {

        }
      })


    Thread.sleep(30*1000)
  }
}
