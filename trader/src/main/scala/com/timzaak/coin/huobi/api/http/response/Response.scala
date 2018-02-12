package com.timzaak.coin.huobi.api.http.response

trait Response {
  def status: S

  def isSuccess: B = status == "ok"

}


