package com.timzaak.api.binance

import com.timzaak.di.ConfigDI
import org.scalatest.{FreeSpec, Matchers}
import ws.very.util.json.JsonHelperWithDoubleMode


// sbt "testOnly com.timzaak.api.binance.BinanceWSClientTest"
class BinanceWSClientTest extends FreeSpec with Matchers with ConfigDI with JsonHelperWithDoubleMode {

  "aggTrade" in {
    val client = new BinanceWSClient()
    client.aggTradeStream("btcusdt", (new BinanceWSClient.TradeListener {

    }))

    Thread.sleep(20* 1000)
    1 shouldBe 1
  }
}
