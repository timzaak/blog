package com.timzaak.coin.huobi

import java.net.URLEncoder
import java.time.{LocalDateTime, ZoneId}

import com.timzaak.coin.huobi.entity.DepthType.DepthType
import com.timzaak.coin.huobi.request.KlineRequest
import com.timzaak.coin.huobi.response.{DepthResponse, KlineResponse, TradeResposne}
import org.json4s.Formats
import very.util.http.Http4SRequestWrapper
import ws.very.util.json.JsonHelperWithDoubleMode
import ws.very.util.security.HmacSHA256

import scalaj.http.{Http, HttpRequest, HttpResponse}

trait HttpHuobiApi extends HuobiApi with Http4SRequestWrapper with JsonHelperWithDoubleMode {

  protected def marketHost = s"$host/market"

  protected def tradeHost = s"$host/v1"

  protected def signedQueryString(params: Seq[(String, String)]): S = {
    val queryString = params.sortBy(_._1).map { case (k, v) => s"$k=$v" }.mkString("&")
    s"$queryString&Signature=${HmacSHA256.base64(secretKey, queryString)}"
  }

  implicit class HttpWrapper(request: HttpRequest) {
    def as[T](implicit formats: Formats, mf: scala.reflect.Manifest[T]): Either[HttpResponse[String], T] = {
      val jsonResult = request.jsonResult
      println(request.asString.body)
      jsonResult.map(_.extract[T])
    }

  }
  private val getHeaders =Map(
    "Content-Type" -> "application/x-www-form-urlencoded",
    "Accept-Language" -> "zh-cn",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36"
  )

  protected def authGet(url: S, cc: Product):HttpRequest = {
    val values = cc.productIterator
    val params = cc.getClass.getDeclaredFields.map(_.getName -> values.next).foldLeft(List.empty[(S, S)]) {
      case (result, (key, None)) => result
      case (result, (key, Some(value))) => (key-> value.toString) :: result
      case (result, (key, value)) => (key->value.toString) :: result
    }
    authGet(url, params)
  }

  protected def authGet(url:S, params:Seq[(S,S)]):HttpRequest = {
    val queryString = params.map{
      case (key,value) => URLEncoder.encode(key)-> URLEncoder.encode(value.toString)
    } ++ Seq(
      "AccessKeyId" -> accessKey,
      "SignatureMethod" -> "HmacSHA256",
      "SignatureVersion" -> "2",
      "Timestamp" -> URLEncoder.encode(LocalDateTime.now(ZoneId.of("UTC")).toString))

    Http(s"$url?${signedQueryString(queryString)}").headers(getHeaders)
  }

  protected def get(api:S) = Http(s"$tradeHost/$api").headers(getHeaders)

  def kline(param: KlineRequest) = authGet(s"$marketHost/history/kline", param).as[KlineResponse]

  def currencys = get("common/currencys").asString

  def depth(symbol:S, typ:DepthType) = authGet(s"$marketHost/depth",Seq(
    "symbol"->symbol,
    "type"->typ.toString
  )).jsonResult.map(DepthResponse)

  def trade(symbol:S) = authGet(s"$marketHost/trade",Seq(
    "symbol"->symbol
  )).as[TradeResposne]
}
