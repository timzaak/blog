package com.timzaak.coin.binance

import com.timzaak.di.ConfigDI
import org.scalatest._

//sbt "testOnly com.timzaak.coin.binance.BinanceClientTest"
class BinanceClientTest extends FreeSpec with Matchers with ConfigDI {
  val client = new BinanceClient(
    ApiKey = conf.getString("binance.apiKey"),
    ApiSecret = conf.getString("binance.apiSecret")
  )

  "ping" in {
    client.ping.is2xx shouldBe true
  }

  "encode" in {
    val param = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTCquantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559"
    val encodeText = client.encode(param, "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j")
    encodeText shouldBe "0fd168b8ddb4876a0358a8d14d0c9f3da0e9b20c5d52b2a00fcf7d1c602f9a77"
  }

}
