package define

trait MarketInfo {
  def baseCoin: Coin
  def buyCost[T](mount:BigDecimal, price:BigDecimal):Cost[T]
  def sellCost[T](mount:BigDecimal,price:BigDecimal):Cost[T]
}

object MarketInfo {

  abstract class MarketInfoAbstract(baseCoin: Coin, coinBit: BigDecimal,protected val cost:Map[Coin,Asset]) extends MarketInfo {


  }
}
