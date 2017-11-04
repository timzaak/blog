package com.timzaak.chaos

import java.io.ByteArrayOutputStream

import com.sksamuel.avro4s.{AvroInputStream, AvroOutputStream, AvroSchema, SchemaFor}
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import com.timzaak.TestSpec

import scala.util.Success
// sbt "testOnly com.timzaak.chaos.AvroSeriaTest"
class AvroSeriaTest extends TestSpec {
  case class Recursive(payload: Int, next: Option[Recursive])

  implicit val schemaFor = SchemaFor[Recursive]
  "recursive class" - {
    "work correctly" in {
      val schema             = AvroSchema[Recursive]
      schema.toString shouldBe """{"type":"record","name":"Recursive","namespace":"com.timzaak.chaos","fields":[{"name":"payload","type":"int"},{"name":"next","type":["null","Recursive"]}]}"""
    }
  }

  "json" - {
    "work correctly" in {
      val r = Recursive(1, None)
      val baos = new ByteArrayOutputStream()
      val output = AvroOutputStream.json[Recursive](baos)
      output.write(r)
      output.close()
      val json = baos.toString("UTF-8")
      baos.close()
      json shouldBe """{"payload":1,"next":null}"""
      val in = new ByteInputStream(json.getBytes("UTF-8"),json.size)
      val input = AvroInputStream.json[Recursive](in)
      val result = input.singleEntity
      input.close()
      result shouldBe Success(r)

    }
  }
}
