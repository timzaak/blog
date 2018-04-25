package com.timzaak.watcher

import com.timzaak.api.huobi.entity.Trade
import com.timzaak.data.dsl.HuobiTradeDetailData
import monix.execution.Scheduler
import test.TrendFollow.{bounce, dataStream, direction, price, profileProtect, symbol, will_profiling}

trait TrendFollow {
  def scheduler:Scheduler
  def price: BigDecimal
  def mount: BigDecimal
  def willMount: BigDecimal
  def direction :String
  def symbol:String

  val bounce = BigDecimal(0.06)
  val stopPrice = (if (direction == "buy") 1 - bounce / 2 else (1 + bounce / 2)) * price
  val allowBouncing = bounce / 10
  var will_profiling = BigDecimal(0)

  def stop_profiling  = { will_profiling * 0.8}




  def profileProtect= (dataStream: HuobiTradeDetailData) =>  (trade: Trade) => {
    direction match {
      case "buy" if trade.data.exists(_.price < stopPrice) =>
        println(s"必须 buy，止损,${trade.data}")
        dataStream.cancelable.cancel()
        System.exit(1)
      case "sell" if trade.data.exists(_.price > stopPrice) =>
        println(s"必须 sell，止损,${trade.data}")
        dataStream.cancelable.cancel()
      case "buy" =>
        val may_buy_price = trade.data.map(_.price).max
        val may_now_profiling = (may_buy_price - price) / price
        if (will_profiling < may_now_profiling) {
          will_profiling = may_now_profiling
        }
        if (may_now_profiling.abs < allowBouncing || may_now_profiling > stop_profiling) {

        } else if (may_now_profiling < stop_profiling) {
          //趋势终止，买入
          println(s"趋势终止，买入, 价格$may_buy_price")
          dataStream.cancelable.cancel()
        }
      case "sell" =>
        val may_sell_price = trade.data.map(_.price).min
        val may_now_profiling = (price - may_sell_price) / price
        if (will_profiling < may_now_profiling) {
          will_profiling = may_now_profiling
        }
        if (may_now_profiling.abs < allowBouncing || may_now_profiling > stop_profiling) {

        } else if (may_now_profiling < stop_profiling) {
          println(s"趋势终止，卖出, 价格 $may_sell_price")
          dataStream.cancelable.cancel()

        }
    }
  }


  def go(dataStream: HuobiTradeDetailData) = {
    val p = profileProtect(dataStream)
    dataStream.map(p).runAsyncGetLast
  }
}
