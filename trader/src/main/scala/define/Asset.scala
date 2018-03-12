package define

trait Asset {
  def label: String
  def currency: Coin
  def coin: Coin
  def minSize: BigDecimal // ??最小交易单位
  def maxSize: BigDecimal //最大交易量
  def increment: BigDecimal
}

object Asset {

  case class DefaultAsset(
                           label: String,
                           currency: Coin,
                           coin: Coin,
                           minSize: BigDecimal,
                           maxSize: BigDecimal,
                           increment: BigDecimal
                         ) extends Asset

}