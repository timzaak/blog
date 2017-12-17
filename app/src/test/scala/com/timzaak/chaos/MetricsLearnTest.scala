package com.timzaak.chaos

import java.util.concurrent.TimeUnit

import com.codahale.metrics.ConsoleReporter
import org.scalatest._
import nl.grons.metrics.scala.DefaultInstrumented

class MetricsLearnTest extends FreeSpec with Matchers {

  "metrics" - {
    "hello world " in new DefaultInstrumented {
      val reporter = ConsoleReporter.forRegistry(metricRegistry)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.SECONDS)
        .build()
      reporter.start(100, TimeUnit.MILLISECONDS)
      val loading = metrics.timer("timer")
      loading.time {
        Thread.sleep(1000)
      }

      1 shouldBe 1
    }
  }

}
