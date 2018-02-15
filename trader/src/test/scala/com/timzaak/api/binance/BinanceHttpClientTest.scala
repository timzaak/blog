package com.timzaak.api.binance

import com.timzaak.api.binance.api.http.request.{DepthRequest, KlinesRequest}
import com.timzaak.api.binance.api.http.response.KlinesResponse
import com.timzaak.api.binance.entity.Interval
import com.timzaak.di.ConfigDI
import org.scalatest._
import ws.very.util.json.JsonHelperWithDoubleMode

//sbt "testOnly com.timzaak.api.binance.BinanceHttpClientTest"
class BinanceHttpClientTest extends FreeSpec with Matchers with ConfigDI with JsonHelperWithDoubleMode {
  val client = new BinanceHttpClient(
    ApiKey = conf.getString("binance.apiKey"),
    ApiSecret = conf.getString("binance.apiSecret")
  )

  "ping" ignore {
    client.ping.is2xx shouldBe true
  }

  "encode" ignore {
    val param = "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTCquantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559"
    val encodeText = client.encode(param, "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j")
    encodeText shouldBe "0fd168b8ddb4876a0358a8d14d0c9f3da0e9b20c5d52b2a00fcf7d1c602f9a77"
  }
  "time" ignore {
    client.time.is2xx shouldBe true
  }

  "depth" ignore {
    println(client.depth(DepthRequest("LTCBTC")).asString)
    1 shouldBe 1
  }

  "klines response parse" ignore {
    val stringResponse =
      """
        |[
        |		  [
        |		    1499040000000,
        |		    "0.01634790",
        |		    "0.80000000",
        |		    "0.01575800",
        |		    "0.01577100",
        |		    "148976.11427815",
        |		    1499644799999,
        |		    "2434.19055334",
        |		    308,
        |		    "1756.87402397",
        |		    "28.46694368",
        |		    "17928899.62484339"
        |		  ]
        |		]
      """.stripMargin

    val response = KlinesResponse.parse(parseJson(stringResponse))

    response.head.openTime shouldBe 1499040000000L
  }

  "klines" ignore {
    client.klines(KlinesRequest(
      "LTCBTC",
      Interval.`1m`
    )).isRight shouldBe true
  }

  "allPrices" in {
    client.allPrices.isRight shouldBe true
  }

}
