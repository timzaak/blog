package test

import define.Coin
import define.Coin._

import scala.math.BigDecimal.RoundingMode._

//sbt "runMain test.PriceAndRatio"
object PriceAndRatio extends App{
  def expectByRatio(nowPrice: BigDecimal,nowRatio:BigDecimal, expectedRatios:Seq[BigDecimal], mount:BigDecimal)= {
    val beginPrice =nowPrice/(nowRatio + 1)
    expectedRatios.foreach{ratio =>
      val actionPrice = beginPrice * (1 + ratio)
      println(s"$ratio ${actionPrice.setScale(2,DOWN)}  ${((beginPrice-actionPrice)*mount * 0.998).setScale(2,DOWN)}")
    }
  }

  expectByRatio(9173,-0.005,(BigDecimal(0) to BigDecimal(-0.03) by -0.005), 0.5)
}
