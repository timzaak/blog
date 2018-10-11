package com.timzaak

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.scalatest.selenium.{Driver, WebBrowser}



object SingleThreadCrawler extends App with WebBrowser with Driver {

  System.setProperty("webdriver.chrome.driver",
    """src/main/resources/chromedriver""")

  override implicit val webDriver: WebDriver = new ChromeDriver()
  val host = "https://www.baidu.com"
  go to (host)
  assert(pageTitle == "百度一下，你就知道")
}
