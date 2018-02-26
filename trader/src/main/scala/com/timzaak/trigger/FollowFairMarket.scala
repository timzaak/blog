package com.timzaak.trigger

import monix.execution.{Cancelable, Scheduler}
import monix.reactive.{Consumer, Observable, Observer, OverflowStrategy}
import cats.data._
import monix.execution.atomic.Atomic
import monix.execution.cancelables.BooleanCancelable
import monix.reactive.observers.Subscriber

import scala.collection.immutable.Queue

trait FollowFairMarket {
  import FollowFairMarket._
   //(Long,D,D) timestamp, count, price
  implicit def scheduler:Scheduler

  def fairMarketBuyTrade:Observable[(Long,Double, Double)]

  def slowMarketBuyTrade:Observable[(Long,Double, Double)]
  //每次购买最低利润 money／earnMoney = alpha
  def alpha:D = 0.0001


  def buyFee:D = {
    //默认 huobi
    0.002
  }
  def sellFee:D = 0.002

  def fairMarketAvgBuyPrice(recentCount:I):Observable[Double] = {
    fairMarketBuyTrade.bufferSliding(recentCount,1).map{seq =>
      seq.foldLeft(0d)(_ + _._3)/recentCount
    }
  }



  def marketPriceDiffAvg(recentCount:I):Observable[Double] = {
    fairMarketBuyTrade.combineLatest(slowMarketBuyTrade).bufferSliding(recentCount, 1).map{v=>
      v.foldLeft(0d){case (sum, item)=> sum + item._2._3 - item._1._3 } / recentCount
      //val variance = v.foldLeft(0d){case (sum,item) =>  Math.pow((item._2._3 - item._1._3)- avg,2)}/ recentCount
      //avg
    }
  }

  def calculate(recentCount:I, allowLatency:L) = {
    Observable.unsafeCreate[(D,D,D)] { subscribe =>
      val task = Observable.merge(fairMarketBuyTrade.map("f" -> _), slowMarketBuyTrade.map("s" -> _)).foldLeftL(Cache()) {
        case (c, (symbol, (time, _, price))) =>
          val nowTime = now.mill
          if (symbol == "f") {
            if (c.recentFairPrice.size >= recentCount && c.recentDiffPrice.size >= recentCount && nowTime - time <= allowLatency) {
              val diffAvg = c.getDiffPriceAvg
              //(==<><<>..,854.98,853.67,849.92,0.2547607037723765)

              val sellPrice = BigDecimal(diffAvg + c.fairPrice).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
              //println(diffAvg,c.slowPrice, (1+ alpha)*c.slowPrice/((1-buyFee)*(1-sellFee)) - sellPrice)
              val profile = sellPrice - (1 + alpha) * c.slowPrice / ((1 - buyFee) * (1 - sellFee))
              if (profile > 0) {
                subscribe.onNext((c.slowPrice,sellPrice, profile))
                println("==<><<>..", c.slowPrice, sellPrice, price, profile)
              }
            }
            val newRecentFairPrice = (if (c.recentFairPrice.size >= recentCount) {
              c.recentFairPrice.dequeue._2
            } else {
              c.recentFairPrice
            }).enqueue(price)
            c.copy(fairTimestamp = time, fairPrice = price, recentFairPrice = newRecentFairPrice)
          } else {
            if (c.recentFairPrice.size >= recentCount && c.recentDiffPrice.size >= recentCount && nowTime - time <= allowLatency) {
              val diffAvg = c.getDiffPriceAvg
              val sellPrice = BigDecimal(diffAvg + c.fairPrice).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
              val profile = sellPrice - (1 + alpha) * price / ((1 - buyFee) * (1 - sellFee))
              //println(diffAvg,price, (1+ alpha)*c.slowPrice/((1-buyFee)*(1-sellFee))-sellPrice)
              if (profile > 0) {
                subscribe.onNext((price,sellPrice, profile))
                println("=====>...", price, sellPrice, c.fairPrice, profile)
              }
            }
            if (c.fairTimestamp > 0) {
              var newRecentDiffPrice = c.recentDiffPrice.enqueue(price - c.fairPrice)
              newRecentDiffPrice = if (newRecentDiffPrice.size > recentCount) {
                newRecentDiffPrice.dequeue._2
              } else {
                newRecentDiffPrice
              }
              c.copy(slowTimestamp = time, slowPrice = price, recentDiffPrice = newRecentDiffPrice)
            } else {
              c.copy(slowTimestamp = time, slowPrice = price)
            }
          }

      }.runAsync
      task.onComplete(_ => subscribe.onComplete())
      task.failed.map{error => subscribe.onError(error)}(scheduler)
      Cancelable{
        task.cancel
      }

    }
  }

}

object FollowFairMarket{
  case class Cache(
    slowTimestamp:L = 0L,
    slowPrice:D =0d,
    fairTimestamp:L =  0l,
    fairPrice:D = 0d,
    recentFairPrice:Queue[D]= Queue.empty,
    recentDiffPrice:Queue[D] = Queue.empty
    ){

    def getDiffPriceAvg:D = recentDiffPrice.foldLeft(0D)(_+_)/recentDiffPrice.size
    def getFairPriceAvg:D = recentFairPrice.foldLeft(0D)(_+_)/recentFairPrice.size

  }



}
