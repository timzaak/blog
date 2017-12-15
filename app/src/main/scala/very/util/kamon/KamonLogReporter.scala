package very.util.kamon

import java.time.{Instant, ZoneId}

import com.joyrec.util.log.impl.slf4j.{ClassSlf4j, WithSlf4j}
import com.typesafe.config.Config
import kamon.metric.TickSnapshot
import kamon.trace.Span
import kamon.{MetricReporter, SpanReporter}
import ws.very.util.lang.DateFormats

// 不支持 tag
trait KamonLogReporter extends MetricReporter with SpanReporter with WithSlf4j {

  val dateFormat = DateFormats.`yyyy-MM-dd HH:mm:ss`
  val zone = ZoneId.systemDefault()
  private def tickFormat(snapshot: TickSnapshot) = {
    val logStr = StringBuilder.newBuilder
//    val from = dateFormat.format(Instant.ofEpochMilli(snapshot.interval.from).atZone(zone))
//    val to = dateFormat.format(Instant.ofEpochMilli(snapshot.interval.to).atZone(zone))

    if (snapshot.metrics.histograms.nonEmpty) {
      logStr.append(
        """
          |==== Histograms ====
        """.stripMargin)
      snapshot.metrics.histograms.foreach { histogram =>
        //val name : _root_.scala.Predef.String, val tags : kamon.Tags, val unit : kamon.metric.MeasurementUnit, val dynamicRange : kamon.metric.DynamicRange, val distribution : kamon.metric.Distribution
        val distribution = histogram.distribution
        logStr.append(
          """
            |%-20s max:%-10d min:%-10d count:%-10d
          """.stripMargin.format(histogram.name, distribution.max, distribution.min, distribution.count)
        )
      }
      logStr.append("==== Histograms End ====")
    }
    if (snapshot.metrics.counters.nonEmpty) {
      logStr.append(
        """
          |==== Counter ====
        """.stripMargin)
      snapshot.metrics.counters.foreach { counter =>
        val
        value = counter.value * counter.unit.magnitude.scaleFactor
        logStr.append(
          """
            |%-20s %-10f %s                                                                                                 |
          """.stripMargin.format(counter.name, value, counter.unit.magnitude.name))
      }
      logStr.append(
        """
          |==== Counter End ====
        """.stripMargin
      )
    }

    if (snapshot.metrics.gauges.nonEmpty) {
      logStr.append(
        """
          |==== Gauges ====
        """.stripMargin)
      //val name : String, val tags : kamon.Tags, val unit : kamon.metric.MeasurementUnit, val value : scala.Long
      snapshot.metrics.gauges.foreach { gauge =>
        val value = gauge.value * gauge.unit.magnitude.scaleFactor
        logStr.append(
          """
            |%-20s %-10f %s
          """.stripMargin.format(gauge.name, value, gauge.unit.magnitude.name)
        )
      }
      logStr.append(
        """
          |==== Gauges End ====
        """.stripMargin)


    }
    if (snapshot.metrics.rangeSamplers.nonEmpty) {
      logStr.append(
        """
          |==== RangeSamplers ====
        """.stripMargin)
      snapshot.metrics.rangeSamplers.foreach { rangeSampler =>
        val distribution = rangeSampler.distribution
        logStr.append(
          """
            |%-20s max:%-10d min:%-10d count:%-10d
          """.stripMargin.format(rangeSampler.name, distribution.max, distribution.min, distribution.count)
        )

      }
      logStr.append(
        """
          |==== RangeSamplers ====
        """.stripMargin)
    }
    logStr.toString()

  }

  override def reportTickSnapshot(snapshot: TickSnapshot): Unit = {
    println(tickFormat(snapshot))
  }

  private def spansFormat(spans: Seq[Span.FinishedSpan]) = {
    val logStr = StringBuilder.newBuilder
    logStr.append(
      """
        |==== FinishedSpan ====
      """.stripMargin)
    spans.foreach { span =>
      logStr.append(
        """
          | %-20s: duration: %d
        """.stripMargin.format(span.operationName, span.endTimestampMicros - span.startTimestampMicros)
      )

    }
    logStr.append(
      """
        |==== FinishedSpan End ====
      """.stripMargin)
    logStr.toString
  }

  override def reportSpans(spans: Seq[Span.FinishedSpan]): Unit = {
    if(spans.nonEmpty){
      println(spansFormat(spans))
    }

  }

  override def start(): Unit = {
    println("start reporter")
  }

  override def stop(): Unit = {
    println("stop reporter")
  }

  override def reconfigure(config: Config): Unit = ()
}


object KamonLogReporter extends KamonLogReporter with ClassSlf4j {

}



