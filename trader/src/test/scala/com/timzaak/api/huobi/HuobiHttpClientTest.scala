package com.timzaak.api.huobi


import com.timzaak.api.huobi.api.http.request.KlineRequest
import com.timzaak.api.huobi.api.http.entity.{DepthType, Period}
import com.timzaak.api.huobi.api.http.response.DepthResponse
import com.timzaak.di.ConfigDI
import org.scalatest.{FreeSpec, Matchers}
import ws.very.util.json.JsonHelperWithDoubleMode

//sbt "testOnly com.timzaak.coin.huobi.HuobiClientTest"
class HuobiHttpClientTest extends FreeSpec with Matchers with ConfigDI with JsonHelperWithDoubleMode {
  val client = new HuobiHttpClient(
    conf.get[S]("huobi.accessKey"),
    conf.get[S]("huobi.secretKey")
  )

  "kline" ignore {
    val response = client.kline(KlineRequest("ltcbtc", Period.`1min`))

    response.isRight shouldBe true
  }

  "currencys" ignore {
    val response = client.currencys
    response.is2xx shouldBe true
  }

  "depth response parse" ignore {
    val jsonString =
      """
        |{"status":"ok","ch":"market.ltcbtc.depth.step2","ts":1514567951179,"tick":{"bids":[[0.016900,36.9417],[0.016800,356.5203],[0.016700,820.4881],[0.016600,1052.3139],[0.016500,959.3745],[0.016400,1407.6649],[0.016300,126.2599],[0.016200,103.4709],[0.016100,56.9727],[0.016000,246.4974],[0.015900,14.3462],[0.015800,67.0351],[0.015700,0.6000],[0.015600,28.8152],[0.015500,14.1000],[0.015400,9.7000],[0.015300,49.0504],[0.015200,12.5423],[0.015100,20.0000],[0.015000,469.5781]],"asks":[[0.017100,110.1523],[0.017200,99.4006],[0.017300,301.3228],[0.017400,113.6793],[0.017500,396.6380],[0.017600,208.4455],[0.017700,1105.4492],[0.017800,119.2925],[0.017900,495.5713],[0.018000,10.9105],[0.018100,101.9914],[0.018200,0.1000],[0.018300,102.8534],[0.018400,31.2787],[0.018500,431.9090],[0.018600,5.9245],[0.018700,150.9944],[0.018800,9.1746],[0.018900,100.0000],[0.019000,130.7073]],"ts":1514567951031,"version":885503427}}
      """.stripMargin

    val depthResponse = DepthResponse(parseJson(jsonString))
    depthResponse.status shouldBe "ok"
  }

  "depth" ignore {
    val response = client.depth("ltcbtc", DepthType.step2)
    response.isRight shouldBe true
  }



}
