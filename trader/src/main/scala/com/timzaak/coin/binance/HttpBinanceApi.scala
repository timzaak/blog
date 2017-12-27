package com.timzaak.coin.binance

import java.net.URLEncoder

import com.timzaak.coin.binance.request.KlinesRequest
import very.util.http.Http4SRequestWrapper
import scalaj.http.Http
import ws.very.util.security.HmacSHA256

trait HttpBinanceApi extends BinanceApi with Http4SRequestWrapper {

  private def getQueryString(cc: Product) = {
    val values = cc.productIterator
    val params = cc.getClass.getDeclaredFields.map(_.getName -> values.next).foldLeft(List.empty[(S, S)]) {
      case (result, (key, None)) => result
      case (result, (key, Some(value))) => (URLEncoder.encode(key), URLEncoder.encode(value.toString)) :: result
      case (result, (key, value)) => (URLEncoder.encode(key), URLEncoder.encode(value.toString)) :: result
    }.mkString("&")
    s"$params&signature=${encode(params)}"
  }


  val host = "https://api.binance.com/api/v1"

  def get(api: S, param: Product) = {
    Http(s"$host/$api?${getQueryString(param)}")
  }

  private def req(api: S) = Http(s"$host/$api").header("X-MBX-APIKEY", ApiKey)

  //private def
  def ping = req("ping").asString

  def encode(params: S, secret: S = ApiSecret): S = {
    HmacSHA256(secret, params).toLowerCase
  }

  def klines(param: KlinesRequest) = {
    get("klines", param)
  }



}
