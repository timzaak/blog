package com.timzaak.chaos

import org.scalatest.{FreeSpec, Matchers}

import scala.reflect.api.TypeTags
// sbt "testOnly com.timzaak.chaos.ImplicitlyTest"
class ImplicitlyTest extends FreeSpec with Matchers{

  trait Extract[A]{
    def extract(a:A):String
  }


  object AScope{
    implicit object StringExtract extends Extract[String]{
      override def extract(a: String): String = a
    }
    implicit object IntExtract extends Extract[Int]{
      override def extract(a: Int): String = a.toString
    }
  }
  object BScope{
//    def implicitlyHandler[A:TypeTags](a:A) = {
//      implicitly[Extract[A]].extract(a)
//    }
  }

  "hello world" in {
    //import AScope._
    implicit object StringExtract extends Extract[String]{
      override def extract(a: String): String = a
    }
    implicit object IntExtract extends Extract[Int]{
      override def extract(a: Int): String = a.toString
    }
//    BScope.implicitlyHandler(1) shouldBe "1"
//    BScope.implicitlyHandler("a") shouldBe "a"
  }
}
