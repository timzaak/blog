package com.timzaak.chaos

import org.scalatest.{FreeSpec, Matchers}
import cats._
import data._
import free._
import Free.liftF
import cats.arrow.FunctionK
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
    val first: List ~> Option = λ[List ~> Option](_.headOption)

    first(List(1,2)) shouldBe Some(1)
    val firstA = new (List~>Option){
      def apply[A](fa:List[A]):Option[A] = fa.headOption
    }
    firstA(List(1,2)) shouldBe Some(1)

    val firstB = new FunctionK[List,Option]{
      override def apply[A](fa: List[A]): O[A] = fa.headOption
    }
    firstB(List(1,2)) shouldBe Some(1)

  }
  "Kleisli" in {
    val parse = Kleisli[Option,String,Int]((s: String) => if (s.matches("-?[0-9]+")) Some(s.toInt) else None)
    val reciprocal:Kleisli[Option,Int,Double] = Kleisli((i: Int) => if (i != 0) Some(1.0 / i) else None)

    val abc = parse.andThen(reciprocal)
    abc("1") shouldBe Some(1.0d)
    parse.andThen(reciprocal).apply("1") shouldBe Some(1.0d)
  }

  case class User(name:String,pwd:String)
  sealed trait FormvalidatorNel {
    type ValidationResult[A] = ValidatedNel[String, A]

    private def validateUserName(userName:String) =
      if (userName.matches("^[a-zA-Z0-9]+$")) userName.validNel else "bad user name".invalidNel

    private def validatePasswored(password:String): ValidationResult[String] =
      if (password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$")) password.validNel[String]
      else "bad password".invalidNel

    def valid(userName:String, password:String):ValidationResult[User] = {
      (validateUserName(userName),
        validatePasswored(password)).mapN(User)

    }

  }
  object FormvalidatorNel extends FormvalidatorNel

  "Validated" in {
    (Right("21"),Right("d")).mapN(User)
    val result = FormvalidatorNel.valid("123123123123321","1231123zZE-23")
    result.toEither.isRight shouldBe true
  }

}
