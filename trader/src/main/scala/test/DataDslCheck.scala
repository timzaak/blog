package test

import com.timzaak.data.dsl.{BinanceAggTradeDetailData, HuobiTradeDetailData}
import monix.reactive.{Consumer, Observable, OverflowStrategy}
import monix.execution.Scheduler.{global => scheduler}
import concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global
// sbt "runMain test.DataDslCheck"
object DataDslCheck extends App{
  //val dataSource = new HuobiTradeDetailData("btcusd")(global)

  //dataSource.consumeWith(Consumer.foreach(println(_))).runAsync(scheduler)

  val huobi = "h"
  val binance = "b"
  def test(symbol:String) = {
    val huobi_ethusdt = new HuobiTradeDetailData(symbol)(global).collect {
      case a if a.data.exists(_.direction == "buy") =>
        val t=  now.mill
        huobi -> (a.ts -> a.data.filter(_.direction == "buy").map(_.price).max)
    }
    val binance_ethusdt = new BinanceAggTradeDetailData(symbol).collect {
      case a if a.m  =>
        binance -> (a.T -> a.p.toDouble)
    }


    case class MarketDiff(aTimestamp:L = 0L, diff:BigDecimal =0, aPrice:BigDecimal = 0, bPrice: BigDecimal = 0, bTimestamp:L = 0L){
      def diffPercent = diff/Math.min(bPrice.toDouble,aPrice.toDouble)
    }


    Observable.merge(binance_ethusdt,huobi_ethusdt)(OverflowStrategy.DropOld(10)).foldLeftL(MarketDiff()){
      case (sum, (typ,(timeStamp, price))) =>
        println(now.mill - timeStamp)
        if(typ == huobi){
          if((now.mill - timeStamp) <= 1000*10 && (now.mill-sum.bTimestamp) <= 1000*10){
            val result = sum.copy(diff = (price - sum.bPrice).abs, aTimestamp = timeStamp, aPrice = price)
            val percent = result.diffPercent.toDouble

              if(sum.diff > price*0.0002*2){
                println((sum.diff - price*0.0002*2)/price)
                println(s"${timeStamp- sum.bTimestamp },${symbol},a1:${percent}, ${price - sum.bPrice}")
              }
              //println(s"${timeStamp- sum.bTimestamp },${symbol},a1:${percent}, ${price - sum.bPrice}")

            result

          }else {
            sum.copy(aTimestamp = timeStamp, aPrice = price)
          }
        }else{
          if((now.mill - timeStamp) <= 1000*10 && (now.mill-sum.aTimestamp) <= 1000*10){
            val result = sum.copy(diff = (price - sum.aPrice).abs, bTimestamp = timeStamp, bPrice = price)
            val percent = result.diffPercent.toDouble
            if(sum.diff > sum.aPrice*0.0002*2){
              println((sum.diff - sum.aPrice*0.0002*2)/sum.aPrice)
              println(s"${sum.aTimestamp - timeStamp },${symbol},a2:${percent}, ${sum.aPrice- price}")
            }
            result
          }else {
            sum.copy(bTimestamp = timeStamp, bPrice = price)
          }
        }
    }.runAsync(scheduler)
  }
  test("btcusdt")
  test("ethusdt")
  test("neousdt")
  test("ltcusdt")





}
