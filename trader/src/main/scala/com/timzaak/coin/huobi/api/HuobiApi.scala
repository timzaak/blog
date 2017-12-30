package com.timzaak.coin.huobi.api

import com.timzaak.coin.huobi.S

trait HuobiApi {

  def accessKey:S
  def secretKey:S

  protected def host = "https://api.huobi.pro"
}
