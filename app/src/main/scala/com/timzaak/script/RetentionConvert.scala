package com.timzaak.script

import java.net.URLEncoder
import java.net.URLEncoder

import com.taobao.api.DefaultTaobaoClient
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest
import org.json4s.JValue
import slick.jdbc.{PositionedParameters, SetParameter}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scalaj.http.Http

object RetentionConvert extends App{
//  implicit val accounts =
//    Database.forURL(url = s"jdbc:postgresql://127.0.0.1:5401/growing_accounts?user=apps&password=${URLEncoder.encode("v,4)5Y|5G1PzF+fL","utf-8")}", driver = "org.postgresql.Driver")


  val phones = List(
    "18611017052",
    "18520862711",
    "13526590394",
    "18850089988",
    "15815800989",
    "13552694202",
    "13816873569",
    "18622660356",
    "18640591889",
    "13917333667",
    "13661293485",
    "17722524745",
    "18302616420",
    "18728494163",
    "15910513056",
    "15910235768",
    "15178752413",
    "18311412798",
    "18792585168",
    "13258386609",
    "18131272771",
    "18716321063",
    "13580437445",
    "18530831945",
    "15108491002",
    "18857099128",
    "15092056986",
    "13618226538",
    "13816681988",
    "15910693379",
    "17783683725",
    "18841261599",
    "18502649758",
    "18515819076",
    "15810384298",
    "13136157625",
    "18515069799",
    "13430667006",
    "13817424070",
    "15013412330",
    "13252033184",
    "18620010372",
    "18998999967",
    "13551361248",
    "15858612156",
    "15659815980",
    "17713647106",
    "18077009277",
    "15017570751",
    "13250743141",
    "13880960452",
    "15923963290",
    "13261653928",
    "15730083565",
    "18758083364",
    "15010998051",
    "18825106013",
    "15361874969",
    "18800052679",
    "13717285876",
    "13688301569",
    "17602135020",
    "18666919772",
    "18401489762",
    "13826261034",
    "13611246961",
    "18806159668",
    "17603722160",
    "18565209684",
    "18910066588",
    "15921571230",
    "15817266261",
    "18501339761",
    "13003636112",
    "18661675371",
    "13837171893",
    "15906639627",
    "18618193536",
    "18210711029",
    "18821273603",
    "17320102701",
    "18801394561",
    "15605761348",
    "13466362379",
    "15652798010",
    "18611857982",
    "15881042088",
    "13530482193",
    "13910758942",
    "15527177981",
    "18819252817",
    "13757106252",
    "18160198946",
    "18301560596",
    "15657168556",
    "13524053705",
    "13536031810",
    "17600666628",
    "15982277725",
    "18826075042",
    "15092056686"
  )
//  def send(mobile:S): Unit = {
//    println(Http("http://cnprd6:8080/v1/mobile/verify_code").param("mobile",mobile).param("uniq","0").get.asString)
//  }
  //phones.foreach(send _)


val lists= """王淑畔,18622660356
             |TAN,18825106013
             |叶斑,15817266261
             |quanzhener,15910235768
             |孙志奇,15923963290
             |陈小姐,13738084541
             |赵小蛮,15178752413
             |kobeaman,18826075042
             |程昌木,13618226538
             |邓磊,15982277725
             |刘银冰,18819252817
             |橙子,13252033184
             |朱婷婷,15068545759
             |秦卫华,13551361248
             |xuyan,13611246961
             |fisher,13917333667
             |李明,15910513056
             |jammie,15810384298
             |陈碧威,17722524745
             |邹贤,18302616420
             |Alex,18210711029
             |刘新鹤,18801394561
             |钟锋,18620010372
             |杨友仁,13816681988
             |王赵培,15906639627
             |邱勇峰,13526590394
             |王轶,18502649758
             |待飞罗,18600070661
             |邓新武,17602135020
             |AnneDu,15361874969
             |王赵培,15906639627
             |张雪梅,13466362379
             |theo,15527177981
             |18530831945,18530831945
             |王丹,18301560596
             |李赞,15910693379
             |邱滋润,13910758942
             |诗酒趁年华,18792585168
             |tony,18311412798
             |官世强,18210711029
             |haonan31210,13580437445
             |黄江涛,15659815980
             |朱,18401489762
             |yoyo,13530482193
             |严乙番,17713647106""".stripMargin.split('\n').map{str=>
    val Array(name,number) = str.split(',')
  name->number
}.toList
  val serverUlr = "http://gw.api.taobao.com/router/rest"
  val appKey ="23490634"
  val appSecret = "1205130a15379b8b02586316b16ac420"
  val client = new DefaultTaobaoClient(serverUlr,appKey,appSecret)

def send(param :(String,String)) ={
  val (name,number) = param
  val req = new AlibabaAliqinFcSmsNumSendRequest
  req.setSmsType("normal")
  req.setSmsFreeSignName("GrowingIO")
  req.setSmsParamString(s"""{"name":"$name"}""")
  req.setRecNum(number)
  req.setSmsTemplateCode("SMS_82135026")

  val response = client.execute(req)
  println(s"isSuccess:${response.isSuccess},phone:$number,error:${response.getErrorCode}")
}
 //println(lists)
  //send(("张朔源","13716379973"))
  lists.foreach(send _)
}