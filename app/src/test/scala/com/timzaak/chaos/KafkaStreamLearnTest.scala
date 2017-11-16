package com.timzaak.chaos

import org.scalatest._
import net.manub.embeddedkafka.streams._
import org.apache.kafka.streams.StreamsBuilder
import net.manub.embeddedkafka.ConsumerExtensions._
import net.manub.embeddedkafka.Codecs.stringKeyValueCrDecoder
import org.apache.kafka.common.serialization.Serdes.StringSerde
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}

class KafkaStreamLearnTest extends FreeSpec with Matchers with EmbeddedKafkaStreamsAllInOne {
  implicit val ser = net.manub.embeddedkafka.Codecs.stringKeyValueCrDecoder
  "kafka stream api" - {
    "hello world" in {
      val inputTopic = "input-topic"
      val outputTopic = "output-topic"

      val stream = new StreamsBuilder
      //stream.stream[String,String](inputTopic).mapValues(v=>s"$v$v").to(outputTopic)
      stream.stream(inputTopic).to(outputTopic)

      runStreamsWithStringConsumer(
        topicsToCreate = Seq(inputTopic, outputTopic),
        topology = stream.build
      ) { consumer =>

        publishStringMessageToKafka(inputTopic,  message = "world")
        consumeFirstStringMessageFrom(outputTopic) should "world"
      }
    }
  }
}
