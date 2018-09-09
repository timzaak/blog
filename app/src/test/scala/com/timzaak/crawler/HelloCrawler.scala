package com.timzaak.crawler
import org.omg.CosNaming.NamingContextPackage.NotEmpty
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.scalatest._
import org.scalatest.selenium.WebBrowser

class HelloCrawler extends FreeSpec with MustMatchers with WebBrowser {
  implicit val webDriver: WebDriver = new HtmlUnitDriver

  val host = "https://www.baidu.com"

  "have the correct title" in {
    go to (host)
    pageTitle must be("百度一下，你就知道")
  }

  "can read one page web " in {
    go to ("http://m.fengjuren.com")
    assert(find(xpath("//label[1]")).nonEmpty)
    pageTitle must be("风巨人")
    click on xpath("//label[1]")
    assert(find(className("repair-h3")).nonEmpty)
  }

}
