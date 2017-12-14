package com.timzaak.chaos


import kamon.Kamon
import kamon.logreporter.LogReporter
import org.scalatest.{FreeSpec, Matchers}

class KamonLearnTest extends FreeSpec with Matchers{
  "kamon" - {
    "counter" in {

      1 shouldBe 1
    }
  }
}
