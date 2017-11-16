package com.timzaak.chaos

import net.manub.embeddedkafka._
import org.json4s.NoTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.scalatest._
import ws.very.util.json.JsonHelperWithDoubleMode

case class Mes(a: String, b: String)

class KafkaLearnTest extends FreeSpec with Matchers with EmbeddedKafka with BeforeAndAfterAll with JsonHelperWithDoubleMode{
  val customBrokerConfig = Map("replica.fetch.max.bytes" -> "2000000",
    "message.max.bytes" -> "2000000")

  val customProducerConfig = Map("max.request.size" -> "2000000")
  val customConsumerConfig = Map("max.partition.fetch.bytes" -> "2000000")
  //implicit val serde = new StringSerde


  implicit val customKafkaConfig = EmbeddedKafkaConfig(
    customBrokerProperties = customBrokerConfig,
    customProducerProperties = customProducerConfig,
    customConsumerProperties = customConsumerConfig)

  override def beforeAll():Unit = {
    super.beforeAll()
    EmbeddedKafka.start
  }

  override def afterAll(): U = {
    super.afterAll()
    EmbeddedKafka.stop
  }

  "kafka api " - {
    //"hello world" in withRunningKafka
    "hello world" in {
      publishStringMessageToKafka("test", "message")
      consumeFirstStringMessageFrom("test") shouldBe "message"
    }
    "json serialize" in {
      val mes = Mes("a","b")
      implicit val formats = Serialization.formats(NoTypeHints)
      publishStringMessageToKafka("test2",write(mes))
      parseJson(consumeFirstStringMessageFrom("test2")).extract[Mes] shouldBe mes
    }
  }
}
