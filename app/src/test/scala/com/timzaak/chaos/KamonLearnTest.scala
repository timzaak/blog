package com.timzaak.chaos

/*
import com.typesafe.config.ConfigFactory
import kamon.{Kamon, MetricReporter, SpanReporter}
import org.scalatest.{FreeSpec, Matchers}
import very.util.kamon.KamonLogReporter

class KamonLearnTest extends FreeSpec with Matchers {
  "kamon" - {
    "counter" in {
      //Kamon.addReporter(KamonLogReporter: SpanReporter, "spanReporter")
      //Kamon.reconfigure(ConfigFactory.parseString("kamon.metric.tick-interval=1s"))
      val counter = Kamon.counter("hello")
      Kamon.addReporter(KamonLogReporter: MetricReporter, "metric")
      for{ i  <- 1 to 20}{
        counter.increment()
        Thread.sleep(1000)
      }
      Kamon.stopAllReporters()
      1 shouldBe 1
    }
  }
}
*/