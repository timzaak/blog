package test

import com.timzaak.api.huobi.entity.Trade
import com.timzaak.data.dsl.HuobiTradeDetailData
import cats.implicits._

// sbt "runMain test.TrendFollow"
object TrendFollow extends App {
  implicit val scheduler = monix.execution.Scheduler.global
  val price = BigDecimal(525.52)
  val mount = BigDecimal(1.5)
  val willMount = BigDecimal(1.51)
  //期望做什么
  val direction = "buy"
  val symbol = "ethusdt"
  val bounce = BigDecimal(0.06)
  val stopPrice = (if (direction == "buy") 1 - bounce / 2 else (1 + bounce / 2)) * price
  val allowBouncing = bounce / 10
  var will_profiling = BigDecimal(0)

  def stop_profiling = {
    will_profiling * 0.8
  }

  val dataStream = new HuobiTradeDetailData(symbol)

  def profileProtect(trade: Trade) = {
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


  (dataStream.map {
    case trade =>
      profileProtect(trade)

  }).runAsyncGetLast


}
