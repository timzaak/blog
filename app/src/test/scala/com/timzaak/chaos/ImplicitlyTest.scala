package com.timzaak.chaos

import org.scalatest.{FreeSpec, Matchers}

import scala.reflect.api.TypeTags

// sbt "testOnly com.timzaak.chaos.ImplicitlyTest"
class ImplicitlyTest extends FreeSpec with Matchers {

  "implicit auto fill param" in {


    //    def add(b:Int,c:Double):Int = b+c.toInt
    //
    //    class A()(implicit b:Int,c:Double) {
    //      def a = add(implicitly,implicitly)
    //    }
    //    implicit val imInt:Int = 3
    //    implicit val imString:Double = 4D
    //    new A().a shouldBe (1+3+4)
  }
  "extend Value function" in {
    trait ExtendValue[T] {
      def value: T
    }

    class WrapValueFunc[A, B <: ExtendValue[A]](trans: A => B) {
        def identityB(implicit e:A):B = trans(implicitly(e))
    }



    new WrapValueFunc((v:String)=> new ExtendValue[String] {def  value = v}).identityB("123").value shouldBe "123"



  }

}
