package test

import define.Coin
import define.Coin._

import scala.math.BigDecimal.RoundingMode._

//sbt "runMain test.PriceAndRatio"
object PriceAndRatio{
  def expectByRatio(nowPrice: BigDecimal,nowRatio:BigDecimal, expectedRatios:Seq[BigDecimal], mount:BigDecimal) :Unit= {
    val beginPrice =nowPrice/(nowRatio + 1)
    expectedRatios.foreach{ratio =>
      val actionPrice = beginPrice * (1 + ratio)
      println(s"$ratio ${actionPrice.setScale(2,DOWN)}  ${((beginPrice-actionPrice)*mount * 0.998).setScale(2,DOWN)}")
    }
  }

  def main(args:Array[String]): Unit = {
    expectByRatio(9334.34,0.0181,BigDecimal(0.05) to BigDecimal(-0.05) by -0.005, 0.3)
  }

}
