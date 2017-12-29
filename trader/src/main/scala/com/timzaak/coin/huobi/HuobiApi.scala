package com.timzaak.coin.huobi

trait HuobiApi {
  def accessKey:S
  def secretKey:S

  protected def host = "https://api.huobi.pro"
}
