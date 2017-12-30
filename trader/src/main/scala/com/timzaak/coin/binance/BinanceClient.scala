package com.timzaak.coin.binance

import com.timzaak.coin.binance.api.HttpBinanceApi
import ws.very.util.json.JsonHelperWithDoubleMode

class BinanceClient(
  val ApiKey: S,
  val ApiSecret: S
) extends HttpBinanceApi with JsonHelperWithDoubleMode{

}
