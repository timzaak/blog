package com.timzaak.dao

import java.time.LocalDateTime
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time.temporal.ChronoField

import com.timzaak.di.DaoDI
import org.scalatest._

import scala.concurrent.Future

//import scala.concurrent.ExecutionContext.Implicits.global
class SlickApiTest extends AsyncFlatSpec with Matchers with DaoDI{
  "Slick" should "deserialize LocalDateTime" in {
    val date2DateTimeFormatter =
      new DateTimeFormatterBuilder()
        .append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        .optionalStart()
        .appendFraction(ChronoField.NANO_OF_SECOND,0,6,true)
        .optionalEnd()
        .toFormatter()
    LocalDateTime.parse("2017-04-21 13:18:56.095",date2DateTimeFormatter).getDayOfMonth
    assert(LocalDateTime.parse("2017-04-21 13:18:56.095",date2DateTimeFormatter).getDayOfMonth == 21)
  }
  it should "deserialize by slick successful" in {
    commentDao.pages(1,1,1).map{_ => assert(true)}
  }
}
