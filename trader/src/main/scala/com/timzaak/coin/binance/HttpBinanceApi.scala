package com.timzaak.coin.binance

import very.util.http.Http4SRequestWrapper

import scalaj.http.Http

trait HttpBinanceApi extends BinanceApi with Http4SRequestWrapper{
  val host = "https://api.binance.com/api/v1"

  private def req(api:S) = Http(s"$host/$api").header("X-MBX-APIKEY",ApiKey)

  def ping = req("ping").asString
  //def ping =
}
