package com.timzaak.chaos

import org.scalatest.{FreeSpec, Matchers}
import cats._
import data._
import free._
import Free.liftF
import implicits._
//sbt "testOnly com.timzaak.chaos.CatsDataTypeLearn"
class CatsDataTypeLearn extends FreeSpec with Matchers {

  sealed trait KVStoreA[A]

  case class Put[T](key: String, value: T) extends KVStoreA[Unit]

  case class GET[T](key: String) extends KVStoreA[Option[T]]

  case class Delete(key: String) extends KVStoreA[Unit]

  type KVStore[A] = Free[KVStoreA, A]

  def put[T](key: String, value: T): KVStore[Unit] =
    liftF[KVStoreA, Unit](Put(key, value))

  def get[T](key: String): KVStore[Option[T]] =
    liftF[KVStoreA, Option[T]](GET[T](key))

  def delete(key: String): KVStore[Unit] =
    liftF[KVStoreA, Unit](Delete(key))

  def update[T](key: String, f: T => T): KVStore[Unit] =
    for {
      vMayBe <- get[T](key)
      _ <- vMayBe.map(v => put[T](key, f(v))).getOrElse(Free.pure(()))
    } yield ()

  "FreeMonads" in {


    //a.compose((b: Id[Double]) => b.toInt)(1.0d) shouldBe "1"
  }

  "FunctionK" in {
    val first: List~> Option =  λ[List~>Option](_.headOption)

  }
}
