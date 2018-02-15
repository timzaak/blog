package com.timzaak.api.huobi

import com.timzaak.api.huobi.api.ws.{HuobiWebSocketListener, RequestTopic}
import org.json4s.JValue
import org.scalatest.{FreeSpec, Matchers}
import ws.very.util.json.JsonHelperWithDoubleMode

// sbt "testOnly com.timzaak.coin.huobi.HuobiWSClientTest"
class HuobiWSClientTest extends FreeSpec with Matchers with JsonHelperWithDoubleMode{
  "hello" in {
    val client:HuobiWSClient =
      new HuobiWSClient(new HuobiWebSocketListener {override def onClosed(client: HuobiWSClient, code: _root_.com.timzaak.api.huobi.api.ws.I, reason: _root_.com.timzaak.api.huobi.api.ws.S): U = ???

        override def onFailure(client: HuobiWSClient, t: Throwable): U = {

        }

        override def onOpen(client: HuobiWSClient): U = {
          //val rt = RequestTopic.depth("btcusdt")
          //val rt = RequestTopic.tradeDetail("btcusdt")
          val rt = RequestTopic.marketDetail("btcusdt")
          client.subscribe(rt)
        }

        override def onMessage(client: HuobiWSClient, message: JValue): U = {

        }
      })


    Thread.sleep(30*1000)
  }
}
