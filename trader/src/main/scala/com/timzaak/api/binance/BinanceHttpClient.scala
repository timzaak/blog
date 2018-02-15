package com.timzaak.api.binance

import com.timzaak.api.binance.api.BinanceHttpApi
import ws.very.util.json.JsonHelperWithDoubleMode

class BinanceHttpClient(
  val ApiKey: S,
  val ApiSecret: S
) extends BinanceHttpApi with JsonHelperWithDoubleMode{

}
