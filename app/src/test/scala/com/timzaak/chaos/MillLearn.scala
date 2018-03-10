package com.timzaak.chaos

import org.scalatest.{FreeSpec, Matchers}


// 边阅读Mill，边学习体验代码
// sbt "testOnly com.timzaak.chaos.MillLearn"
class MillLearn extends FreeSpec with Matchers{

  "Sub Type override return type" in {
    trait P[+R] {
      def map[B](func:R =>B): P[B]
    }
    case class P1[+R](a:R) extends P[R]{
      def map[B](func:R => B): P1[B] = P1(func(a))
    }
    P1(1).map(_.toString) shouldBe P1("1")
  }
}
