package define

trait MarketInfo {
  def baseCoin: Coin

  def getAssetScale(baseCurrency:Coin):BigDecimal

  def coinBit: BigDecimal

}

object MarketInfo {

  abstract class MarketInfoAbstract(baseCoin: Coin, coinBit: BigDecimal,protected val info:Map[Coin,Asset]) extends MarketInfo {

    def getAssetScale(asset:Coin):BigDecimal = {
      info(asset).minSize
    }

//    def getCoinScale(asset:Coin):BigDecimal = {
//      info(asset).increment
//    }
  }
}
