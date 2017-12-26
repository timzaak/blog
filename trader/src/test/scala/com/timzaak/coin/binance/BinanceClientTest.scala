package com.timzaak.coin.binance

import com.timzaak.di.ConfigDI
import org.scalatest._

//sbt "testOnly com.timzaak.coin.binance.BinanceClientTest -Dbinance.apikey -Dbinance.apiSecret"
class BinanceClientTest extends FreeSpec with Matchers with ConfigDI {

  val client = new BinanceClient(
    ApiKey = conf.get[S]("binance.apikey"),
    ApiSecret = conf.get[S]("binance.apiSecret")
  )

  "binanceClient" - {
    "ping" in {
      client.ping.is2xx shouldBe true
    }
  }
}
