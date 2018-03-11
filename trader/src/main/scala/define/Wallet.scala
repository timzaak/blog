package define

import Coin._

trait Wallet {
  def withDrawAllCurrency(coin: Coin): define.Wallet.Currency

  def putCurrency(currency: define.Wallet.Currency): Unit

  def withDrawPossibleCurrency(expectedCurrency: define.Wallet.Currency): define.Wallet.Currency

  def withDrawCurrency(expectedCurrency: define.Wallet.Currency): Option[define.Wallet.Currency]

  def currency(coin:Coin): define.Wallet.Currency
}

object Wallet {
  trait Currency {
    def id: Coin

    def mount: Long

    def unit: BigDecimal

    def toStr: String = s"${id.id}: ${unit * mount}"

    //TODO: different MarketInfo is a problem
    def +(otherMount: Long): Currency

    def -(otherMount: Long): Currency

    def -(otherCurrency: Currency): Currency = this.-(otherCurrency.mount)

    def +(otherCurrency: Currency): Currency = this.+(otherCurrency.mount)

  }

  object Currency {

    object opImplicits extends Ordering.ExtraImplicits {
      implicit val order: Ordering[Currency] = Ordering.by(_.mount)
    }

    def apply(mount:Long, id:Coin)(implicit marketInfo: MarketInfo) = DefaultCurrency(mount,id)

  }

  case class DefaultCurrency(mount: Long, id: Coin = ustd)(implicit marketInfo: MarketInfo) extends Currency {
    def unit: BigDecimal = marketInfo.coinBit

    def +(otherMount: Long): DefaultCurrency = this.copy(mount = mount + otherMount)

    def -(otherMount: Long): DefaultCurrency = {
      val newMount = mount - otherMount
      if (newMount < 0) {
        throw new IndexOutOfBoundsException(s"currency can not be 负数, $mount, $otherMount")
      }
      this.copy(mount = mount + otherMount)
    }
  }

  class DefaultWallet()(implicit marketInfo: MarketInfo) extends Wallet {
    import Currency.opImplicits._
    private val coin2Currency = new java.util.concurrent.ConcurrentHashMap[Coin, Currency]()

    private def zero(coin: Coin) = DefaultCurrency(0, coin)

    def withDrawAllCurrency(coin: Coin): Currency = coin2Currency.getOrDefault(coin, zero(coin))

    def putCurrency(currency: define.Wallet.Currency): Unit = {
      val oldValue = coin2Currency.get(currency.id)
      if (!coin2Currency.replace(currency.id, oldValue, Option(oldValue).map(_ + currency).getOrElse(currency)))
        putCurrency(currency)
    }

    def withDrawPossibleCurrency(expectedCurrency: define.Wallet.Currency): Currency = {
      assert(expectedCurrency.mount >= 0, "withdraw currency should >=0")
      val existedCurrency = coin2Currency.get(expectedCurrency.id)
      existedCurrency match {
        case null =>
          zero(expectedCurrency.id)
        case _ =>
          val withDrawCurrency = if (existedCurrency >= expectedCurrency) expectedCurrency else existedCurrency
          val newExistedCurrency = existedCurrency - withDrawCurrency
          if (!coin2Currency.replace(expectedCurrency.id, existedCurrency, newExistedCurrency)) {
            withDrawPossibleCurrency(existedCurrency)
          } else {
            withDrawCurrency
          }
      }
    }

    def withDrawCurrency(expectedCurrency: define.Wallet.Currency): Option[define.Wallet.Currency] = {
      assert(expectedCurrency.mount >= 0, "withdraw currency should >=0")
      val existedCurrency = coin2Currency.get(expectedCurrency.id)
      existedCurrency match {
        case null =>
          None
        case _ if existedCurrency < expectedCurrency =>
          None
        case _ =>
          val newExistedCurrency = existedCurrency - expectedCurrency
          if (!coin2Currency.replace(expectedCurrency.id, existedCurrency, newExistedCurrency)) {
            withDrawCurrency(existedCurrency)
          } else {
            Some(expectedCurrency)
          }
      }
    }

    def currency(coin:Coin): define.Wallet.Currency = coin2Currency.getOrDefault(coin, zero(coin))

  }

}