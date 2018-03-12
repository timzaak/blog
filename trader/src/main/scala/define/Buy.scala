package define

import scala.concurrent.Future


trait Buy {

  def buy(coin:Coin, mount:BigDecimal)//:Future[Either[BigDecimal]]


}

object Buy {

}
