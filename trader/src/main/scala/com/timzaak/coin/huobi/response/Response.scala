package com.timzaak.coin.huobi.response

trait Response {
  def status: S

  def isSuccess: B = status == "ok"

}


