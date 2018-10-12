package com.timzaak
import java.net.Proxy

import org.jsoup.Jsoup
import scalaj.http.Http

import scala.collection.JavaConverters._
import scala.util.Try

case class ProxyJump(ip:String, port:Int,`type`:String)
object IpCrawler extends App {
  println((new XiCiCrawler {}).getData)
}


trait XiCiCrawler {

  def getData = {
    val body = Http("http://www.xicidaili.com/qq/2").asString.body
    val doc = Jsoup.parse(body)
    //(//tbody/tr)[position()>1]/td[6]
    val results = doc.select("#ip_list tr:not(:eq(0))").listIterator().asScala.foldLeft(List.empty[ProxyJump]){(r,v) =>
      //println(v.select("td:eq(7) div[title]").attr("title").replace("秒",""))
      val speedTime = v.select("td:eq(6) div[title]").attr("title").replace("秒","").toDouble
      val connectionTime = v.select("td:eq(7) div[title]").attr("title").replace("秒","").toDouble
      if(speedTime <= 2 && connectionTime <= 0.8) {
        val ip =  v.select("td:eq(1)").text()
        val port = v.select("td:eq(2)").text().toInt
        val typ = v.select("td:eq(5)").text().toLowerCase
        val result = Try(Http("https://www.baidu.com").timeout(1000,2000).proxy(ip, port,Proxy.Type.SOCKS).asString)
        println(result)
        ProxyJump(ip, port, typ)::r
      } else {
        r
      }
    }

    println("over")

  }
}