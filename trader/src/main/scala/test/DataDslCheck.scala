package test

import com.timzaak.data.dsl.HuobiTradeDetailData
import monix.reactive.Consumer
import monix.execution.Scheduler.{global => scheduler}
import scala.concurrent.ExecutionContext.Implicits.global
// sbt "runMain test.DataDslCheck"
object DataDslCheck extends App{
  val dataSource = new HuobiTradeDetailData("btcustd")(global)

  dataSource.map{v =>
    v.ts
  }.consumeWith(Consumer.foreach(println(_))).runAsync(scheduler)

}
