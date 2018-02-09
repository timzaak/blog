package com.timzaak.chaos

import org.scalatest.{FreeSpec, Matchers}

import shapeless.poly._

// sbt "testOnly com.timzaak.chaos.ShapelessLearn"
/*
 粗略浏览一遍，发现
 Singleton-typed literals
 Singleton-typed Symbols
 HList
 tuple/case class api 拓展(lenses)
 挺好。
 但总感觉自己把控不了。还是专注用好 Cats，等 Scala 3.0 到来吧。

 */
class ShapelessLearn extends FreeSpec with Matchers{
  object choose extends (Set ~> Option) {
    def apply[T](s : Set[T]) = s.headOption
  }

  "choose" in {
    choose(Set(1,2,3)) shouldBe Some(1)
  }
}
