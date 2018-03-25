package test

import com.timzaak.data.dsl.HuobiTradeDetailData

// sbt "runMain test.TrendFollow"
object TrendFollow extends App {
  implicit val scheduler =  monix.execution.Scheduler.global

  val price = BigDecimal(0)
  val mount = BigDecimal(0)
  val willMount = BigDecimal(0)
  val direction = "buy"
  val symbol = "ethusdt"


  (new HuobiTradeDetailData(symbol).collect {
    case trade => println(trade)
  }).runAsyncGetLast


}
