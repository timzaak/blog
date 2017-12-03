package com.timzaak

import com.timzaak.di.{ AkkaDI, WithTestConf }
import com.typesafe.config.{ Config, ConfigFactory }
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{ Millis, Seconds, Span }
import org.scalatest.{ FreeSpec, Matchers }

trait TestSpec extends FreeSpec with Matchers with WithTestConf with ScalaFutures with AkkaDI {
  implicit val defaultPatience =
    PatienceConfig(timeout = Span(10, Seconds), interval = Span(1000, Millis))

}

