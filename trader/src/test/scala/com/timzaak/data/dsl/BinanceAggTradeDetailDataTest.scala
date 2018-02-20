package com.timzaak.data.dsl

import monix.execution.schedulers.TestScheduler
import monix.reactive.Consumer
import org.scalatest._
//  sbt "testOnly com.timzaak.data.dsl.BinanceAggTradeDetailDataTest"
class BinanceAggTradeDetailDataTest extends FreeSpec with Matchers{
  "bi" in {
    implicit val testScheduler = TestScheduler()
    val btcusdt = new BinanceAggTradeDetailData("btcusdt")
    //val ethusdt = new BinanceAggTradeDetailData("ethusdt")
    btcusdt.take(10).countL.runAsync(testScheduler)
    btcusdt.take(10).countL.runAsync(testScheduler)
    //ethusdt.take(10).countL.runAsync(testScheduler)


  }
}
