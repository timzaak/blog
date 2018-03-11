package define

case class Coin(id: String) extends AnyVal{
  def toLowerCase: String = id.toLowerCase
}


object Coin {
  val Array(
  ustd,
  btc
  ) ="""
      |USTD
      |BTC
    """.stripMargin.split('\n').map(Coin.apply)
  ustd
  object Implicits {
    implicit def strToCoin(str:String):Coin = Coin(str.toUpperCase)
  }
}
