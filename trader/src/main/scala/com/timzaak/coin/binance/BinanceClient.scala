package com.timzaak.coin.binance

import ws.very.util.json.JsonHelperWithDoubleMode

class BinanceClient(
  val ApiKey: S,
  val ApiSecret: S
) extends HttpBinanceApi with JsonHelperWithDoubleMode{

}
