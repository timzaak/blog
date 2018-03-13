package define

trait Cost[+T]{

}

object Cost{

  object NoneCost extends Cost[Nothing]

  case class CoinCost(mount:BigDecimal) extends Cost[Coin]

  case class FeeCost(mount:BigDecimal) extends Cost[Nothing]

}