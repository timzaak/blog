package com.timzaak.coin.huobi

import java.net.URLEncoder
import java.time.{LocalDate, LocalDateTime, ZoneId}

import ws.very.util.security.HmacSHA256

trait Api {

  def accessKey: S

  def secretKey: S

  protected def host: S = "https://api.huobi.pro"

  protected def marketHost: S = s"$host/market"

  protected def tradeHost: S = s"$host/v1"

  protected def signedQueryString(params: Seq[(String, String)]): S = {
    val queryString = (params ++
      Seq("AccessKeyId" -> accessKey, "SignatureMethod" -> "HmacSHA256", "SignatureVersion" -> "2", "Timestamp" -> LocalDateTime.now(ZoneId.of("UTC")).toString)).sortBy(_._1).map { case (key, value) =>
      s"${URLEncoder.encode(key)}=${URLEncoder.encode(value)}"
    }.mkString("&")
    s"$queryString&Signature=${HmacSHA256.base64(secretKey, queryString)}"
  }







}
