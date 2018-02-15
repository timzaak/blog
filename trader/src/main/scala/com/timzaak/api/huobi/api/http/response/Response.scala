package com.timzaak.api.huobi.api.http.response

trait Response {
  def status: S

  def isSuccess: B = status == "ok"

}


