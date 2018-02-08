package com.timzaak.chaos

import cats._
import cats.data.Nested
import org.scalatest.{FreeSpec, Matchers}
import cats.implicits._

// sbt "testOnly com.timzaak.chaos.CatsLearn"
class CatsLearn extends FreeSpec with Matchers {
  "monoid" in {
    Monoid[Int].combine(1, 2) shouldBe 3
  }
  "functor" - {
    "simple" in {
      Functor[List].compose[Option].map(List(Some(1), None, Some(2)))(_ + 1)
    }
  }
  "Nested" in {
    val listOption = List(Some(1), None, Some(2))
    val nested: Nested[List, Option, Int] = Nested(listOption)
    // nested: cats.data.Nested[List,Option,Int] = Nested(List(Some(1), None, Some(2)))
    nested.map(_ + 1)
    //    Nested(List(Some(1),None,Some(2))).map(v => v +1)
    //    Nested[List,Option,Int](List(Some(1),None,Some(2))).map(v => v+1)
  }

  "Applicative" in {
    Applicative[Option].pure(1) shouldBe Some(1)
    Applicative[Option].map(Some(1))(_ + 2) shouldBe Some(3)
    Applicative[Option].ap(Some((a: Int) => a.toString))(Some(1)) shouldBe Some("1")
    Applicative[Option].ap(Some((a: Int) => a.toString))(None) shouldBe None
    Applicative[Option].product(Some(2), None) shouldBe None
    Applicative[Option].map3(Some(1), Some(1), Some(2))(_ + _ + _)

    (Some(1), Some(2), Some(3)).mapN(_ + _ + _)
  }

  "Apply" in {
    Apply[Option].ap(Some((v: String) => v.toInt))(Some("1")) shouldBe Some(1)
  }

  "Monad" in {

  }

  "Contravariant" in {

  }



}
