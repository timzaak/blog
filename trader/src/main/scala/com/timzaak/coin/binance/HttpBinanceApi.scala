package com.timzaak.coin.binance

import java.net.URLEncoder

import com.timzaak.coin.binance.request.{DepthRequest, KlinesRequest}
import very.util.http.Http4SRequestWrapper

import scalaj.http.Http
import ws.very.util.security.HmacSHA256

trait HttpBinanceApi extends BinanceApi with Http4SRequestWrapper {

  def encode(params: S, secret: S = ApiSecret): S = {
    HmacSHA256(secret, params).toLowerCase
  }

  private def getQueryString(cc: Product) = {
    val values = cc.productIterator
    cc.getClass.getDeclaredFields.map(_.getName -> values.next).foldLeft(List.empty[(S, S)]) {
      case (result, (key, None)) => result
      case (result, (key, Some(value))) => (URLEncoder.encode(key), URLEncoder.encode(value.toString)) :: result
      case (result, (key, value)) => (URLEncoder.encode(key), URLEncoder.encode(value.toString)) :: result
    }.map{case (k,v)=> s"$k=$v"}.mkString("&")
  }

  private def getSignedQueryString(cc:Product) = {
    val params = getQueryString(cc) + s"&timestamp=${now.mill}"
    s"$params&signature=${encode(params)}"
  }


  val host = "https://api.binance.com/api/v1"

  def get(api: S, param: Product) = {
    Http(s"$host/$api?${getQueryString(param)}").header("X-MBX-APIKEY", ApiKey)
  }

  def getSigned(api:S, param:Product) ={
    Http(s"$host/$api?${getSignedQueryString(param)}").header("X-MBX-APIKEY", ApiKey)
  }

  private def get(api: S) = Http(s"$host/$api").header("X-MBX-APIKEY", ApiKey)

  //private def
  def ping = get("ping").asString

  def time = get("time").asString

  def depth(param:DepthRequest) = get("depth",param)

  def klines(param: KlinesRequest) = {
    get("klines", param).asString
  }



}
