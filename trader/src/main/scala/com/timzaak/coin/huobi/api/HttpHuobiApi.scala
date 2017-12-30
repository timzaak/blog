package com.timzaak.coin.huobi.api

import java.net.URLEncoder
import java.time.{LocalDateTime, ZoneId}

import com.timzaak.coin.huobi.S
import com.timzaak.coin.huobi.api.entity.DepthType.DepthType
import com.timzaak.coin.huobi.api.request.{KlineRequest, OrderPlaceRequest}
import com.timzaak.coin.huobi.api.response.{DepthResponse, KlineResponse, TradeResposne}
import org.json4s.{Formats, JObject}
import very.util.http.Http4SRequestWrapper
import ws.very.util.json.JsonHelperWithDoubleMode
import ws.very.util.security.HmacSHA256
import org.json4s.native.Serialization.write
import scalaj.http.{Http, HttpRequest, HttpResponse}

trait HttpHuobiApi extends HuobiApi with Http4SRequestWrapper with JsonHelperWithDoubleMode {

  protected def marketHost = s"$host/market"

  protected def tradeHost = s"$host/v1"

  protected def signedQueryString(params: Seq[(String, String)] = Seq.empty): S = {
    val queryString = (params ++ Seq(
      "AccessKeyId" -> accessKey,
      "SignatureMethod" -> "HmacSHA256",
      "SignatureVersion" -> "2",
      "Timestamp" -> URLEncoder.encode(LocalDateTime.now(ZoneId.of("UTC")).toString))).sortBy(_._1).map { case (k, v) => s"$k=$v" }.mkString("&")
    s"$queryString&Signature=${HmacSHA256.base64(secretKey, queryString)}"
  }

  implicit class HttpWrapper(request: HttpRequest) {
    def as[T](implicit formats: Formats, mf: scala.reflect.Manifest[T]): Either[HttpResponse[String], T] = {
      val jsonResult = request.jsonResult
      println(request.asString.body)
      jsonResult.map(_.extract[T])
    }

  }

  private val getHeaders = Map(
    "Content-Type" -> "application/x-www-form-urlencoded",
    "Accept-Language" -> "zh-cn",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36"
  )

  private val postHeaders = Map(
    "Content-Type" -> "application/json",
    "Accept-Language" -> "zh-cn",
    "User-Agent" -> "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36"
  )

  protected def authGet(url: S, cc: Product): HttpRequest = {
    val values = cc.productIterator
    val params = cc.getClass.getDeclaredFields.map(_.getName -> values.next).foldLeft(List.empty[(S, S)]) {
      case (result, (key, None|Nil)) => result
      case (result, (key, Some(value))) => (key -> value.toString) :: result
      case (result, (key, values:List[_])) => (key-> values.map(_.toString).mkString(","))::result
      case (result, (key, value)) => (key -> value.toString) :: result
    }
    authGet(url, params)
  }

  protected def authGet(url: S, params: Seq[(S, S)]= Seq.empty): HttpRequest = {
    val queryString = params.map {
      case (key, value) => URLEncoder.encode(key) -> URLEncoder.encode(value.toString)
    }
    Http(s"$url?${signedQueryString(queryString)}").headers(getHeaders)
  }

  protected def get(api: S) = Http(s"$tradeHost/$api").headers(getHeaders)


  protected def authPost(url: S, data: S = "") = {
    Http(s"$url?${signedQueryString()}").headers(postHeaders).postData(data)
  }


  def kline(param: KlineRequest) = authGet(s"$marketHost/history/kline", param).as[KlineResponse]

  def currencys = get("common/currencys").asString

  def depth(symbol: S, typ: DepthType) = authGet(s"$marketHost/depth", Seq(
    "symbol" -> symbol,
    "type" -> typ.toString
  )).jsonResult.map(DepthResponse.apply)

  //TODO: test
  def trade(symbol: S) = authGet(s"$marketHost/trade", Seq(
    "symbol" -> symbol
  )).as[TradeResposne]

  object order {

    private val orderHost = s"$tradeHost/order/orders"

    def place(req: OrderPlaceRequest) = {
      authPost(s"$orderHost/place", write(req))
    }

    def submitCancel(orderId: S) = {
      authPost(s"$orderHost/${orderId}/submitcancel")
    }

    def batchCancel(orderIds: Seq[S]) = {
      authPost(s"$orderHost/batchcancel", toJson("order-ids" -> orderIds))
    }

    def detail(orderId:S) ={
      authGet(s"$orderHost/$orderId")
    }

    def matchresults(orderId:S) = {
      authGet(s"$orderHost/$orderId/matchresults")
    }

    //def search()

  }

  //下单

}

