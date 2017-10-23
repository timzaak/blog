package com.timzaak.chaos

import com.timzaak.TestSpec
import org.scalatest._
import very.util.config.ConfigurationImplicit
class ConfigSpec extends TestSpec with ConfigurationImplicit {
  "config" - {
    "return null with none exist key" in {
      conf.hasPath("one") shouldBe false
      conf.getOptional[S]("one") shouldBe empty
    }

    "can return string" in {
      conf.getOptional[S]("server.salt") shouldBe defined
      conf.getOptional[I]("test.string") shouldBe defined
    }
  }
}
