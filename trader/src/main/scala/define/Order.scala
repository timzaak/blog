package define

trait Order {
  def `type`:Order.OrderType.OrderType

}


object Order {
  object OrderType extends Enumeration{
    type OrderType = Value
    val Limit =  Value
  }
  object OrderStatus extends Enumeration{
    type OrderStatus = Value
    val Cancel, Success, Failure, Doing = Value
  }

  case class LimitOrder(
    coin:Coin,
    amount: BigDecimal,
    time:Long,
    status: OrderStatus.OrderStatus = OrderStatus.Cancel
  ) extends Order{
    override def `type` =  OrderType.Limit
  }

}