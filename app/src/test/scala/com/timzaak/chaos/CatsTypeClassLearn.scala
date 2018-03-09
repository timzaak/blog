package com.timzaak.chaos

import java.util.Date

import cats._
import cats.data.Nested
import org.scalatest.{FreeSpec, Matchers}
import cats.data._
import cats.implicits._

// sbt "testOnly com.timzaak.chaos.CatsTypeClassLearn"
class CatsTypeClassLearn extends FreeSpec with Matchers {
  "monoid" in {
    Monoid[Int].combine(1, 2) shouldBe 3
  }
  "functor" - {
    "simple" in {
      Functor[List].compose[Option].map(List(Some(1), None, Some(2)))(_ + 1)
    }
  }
  "Nested" in {
    val listOption = List(Some(1), None, Some(2))
    val nested: Nested[List, Option, Int] = Nested(listOption)
    // nested: cats.data.Nested[List,Option,Int] = Nested(List(Some(1), None, Some(2)))
    nested.map(_ + 1)
    //    Nested(List(Some(1),None,Some(2))).map(v => v +1)
    //    Nested[List,Option,Int](List(Some(1),None,Some(2))).map(v => v+1)
  }

  "Applicative" in {
    Applicative[Option].pure(1) shouldBe Some(1)
    Applicative[Option].map(Some(1))(_ + 2) shouldBe Some(3)
    Applicative[Option].ap(Some((a: Int) => a.toString))(Some(1)) shouldBe Some("1")
    Applicative[Option].ap(Some((a: Int) => a.toString))(None) shouldBe None
    Applicative[Option].product(Some(2), None) shouldBe None
    Applicative[Option].map3(Some(1), Some(1), Some(2))(_ + _ + _)

    (Option(1), Option(2), Option(3)).mapN(_ + _ + _)
  }

  "Apply" in {
    Apply[Option].ap(Some((v: String) => v.toInt))(Some("1")) shouldBe Some(1)
  }

  "Monad" in {

  }

  case class Money(value: Long)

  case class Salary(size: Money)

  class A

  class B extends A

  "Contravariant" - {
    "simple" in {
      implicit val showMoney: Show[Money] = Show.show[Money](m => s"$$${m.value}")


      implicit val showSalary = showMoney.contramap[Salary](_.size)
      Salary(Money(1000L)).show shouldBe "$1000"
      import scala.math.Ordered._
      implicit val moneyOrdering: Ordering[Money] = Ordering.by(_.value)
      (Money(100L) < Money(200L)) shouldBe true
    }
    "subtyping" in {
      val b = new B
      val a: A = b
      val showA = Show.show[A](_ => "A!")
      val showB: Show[B] = Show.show[B](_ => "B!")
      val showB1: Show[B] = showA.contramap(b => b: A)
      val showB2: Show[B] = showA.contramap(identity[A])
      showA.show(a) shouldBe "A!"
      showA.show(b) shouldBe "A!"
      showB.show(b) shouldBe "B!"
      showB1.show(b) shouldBe "A!"
      showB2.show(b) shouldBe "A!"
      showB2.show(b) shouldBe "A!"
    }
  }

  case class Predicate[A](run: A => Boolean)

  case class Transaction(value: Money, payee: String)

  "ContravariantMonoidal" - {
    implicit val contravariantMonoidal = new ContravariantMonoidal[Predicate] {
      def unit: Predicate[Unit] = Predicate[Unit](Function.const(true))

      override def contramap[A, B](fa: Predicate[A])(f: (B) => A): Predicate[B] = {
        Predicate((b: B) => fa.run(f(b)))
      }

      override def product[A, B](fa: Predicate[A], fb: Predicate[B]): Predicate[(A, B)] = {
        Predicate(x => fa.run(x._1) && fb.run(x._2))
      }

    }

    def isEven = Predicate[Long](_ % 2 == 0)

    def isEvenMoney = isEven.contramap[Money](_.value)

    def liftMoney: Predicate[Long] => Predicate[Money] =
      ContravariantMonoidal[Predicate].liftContravariant(_.value)

    "simple" in {


      def times2Predicate: Predicate[Long] => Predicate[Long] =
        ContravariantMonoidal[Predicate].liftContravariant((x: Long) => x * 2)

      def liftMoney: Predicate[Long] => Predicate[Money] =
        ContravariantMonoidal[Predicate].liftContravariant(_.value)

      def trivial = times2Predicate(isEven)

      isEvenMoney.run(Money(55)) shouldBe false
      trivial.run(2) shouldBe true
      trivial.run(5) shouldBe true
    }

    "implicit" in {
      def isEvan = Predicate[String](_ == "Evan")

      def isGreaterThan50Dollars = liftMoney(Predicate(_ > 50))

      def isEvenPaymentToEvanOfMoreThan50 =
        (isEvenMoney, isGreaterThan50Dollars, isEvan).contramapN(
          (trans: Transaction) => (trans.value, trans.value, trans.payee)
        )

      isEvenPaymentToEvanOfMoreThan50.run(Transaction(Money(56), "Evan")) shouldBe true
    }
  }

  "Invariant" - {
    "simpile" in {
      def longToDate: Long => Date = new Date(_)

      def dateToLong: Date => Long = _.getTime

      implicit val semigroupDate = Semigroup[Long].imap[Date](longToDate)(dateToLong)
      val today = longToDate(1449088684104l)
      val timeLeft = longToDate(1900918893l)

      dateToLong(today |+| timeLeft) shouldBe 1449088684104L + 1900918893L
    }
  }

  case class Foo(a: String, c: List[Double])

  type CSV = List[String]

  trait CsvCodec[A] {
    def read(s: CSV): (Option[A], CSV)

    def write(a: A): CSV
  }

  "Invariant Monoidal" - {
    "simple" in {
      val b = Function.unlift(Foo.unapply)
      implicit val fooSemigroup: Semigroup[Foo] = {
        (implicitly[Semigroup[String]], implicitly[Semigroup[List[Double]]])
          .imapN(Foo.apply)(Function.unlift(Foo.unapply))
      }

      Foo("Hello", List(0.0)) |+| Foo("World", Nil) |+| Foo("!", List(1.1, 2.2)) shouldBe Foo("HelloWorld!", List(0.0, 1.1, 2.2))
    }
    //    "csv" in {
    //
    //    }
  }

  //  case class Foo1(a:Int,b:String)
  //
  //  Foo1(10,"").===(Foo1(10,""))(Eq.fromUniversalEquals[Foo1]) shouldBe true

  "Foldable" in {
    Foldable[List].fold(List("a", "b")) shouldBe "ab"
    Foldable[List].foldMap(List(1, 2, 4))(_.toString) shouldBe "124"
    Foldable[List].foldK(List(List(1, 2, 3), List(2, 3, 4))) shouldBe List(1, 2, 3, 2, 3, 4)
  }

  case class Name(value: String)

  case class Age(value: Int)

  case class Person(name: Name, age: Age)

  "Parallel" - {
    def parse(s:String):Either[NonEmptyList[String], Int] = {
      if (s.matches("-?[0-9]+")) Right(s.toInt)
      else Left(NonEmptyList.one(s"$s is not valid integer."))
    }
    def validateAge(a:Int):Either[NonEmptyList[String], Age] = {
      if(a>18) Right(Age(a))
      else Left(NonEmptyList.one(s"$a is not old enough."))
    }
    def validateName(n:String):Either[NonEmptyList[String],Name] = {
      if(n.length>=8) Right(Name(n))
      else Left(NonEmptyList.one(s"$n does not have enough characters"))
    }

    def parsePerson(ageString:String,nameString:String):Either[NonEmptyList[String],Person] = {
      for {
        age <- parse(ageString)
        person <- (validateName(nameString).toValidated, validateAge(age).toValidated).mapN(Person).toEither
      } yield person

//      for {
//        age <- parse(ageString)
//        person2 <- (validateName(nameString).toValidated, validateAge(age).toValidated).parMapN(Person)
//      } yield person2
    }
    "simple" in {
      parsePerson("18","abcdefgabcabc").isRight shouldBe Right
    }
  }
  case class Person2(name:String,age:Int)
  case class Department(id: Int, name:String)
  "Show" in {
    implicit val showPerson = Show.show[Person2](_.name)
    implicit val showDep = Show.fromToString[Department]
    val john = Person2("John", 31)
    john.show shouldBe "John"
    val engineering = Department(2, "Engineering")
    show"$john works at $engineering" shouldBe "John works at Department(2, Engineering)"

  }

  "Arrow" in {
    val i2s: Int=> String = _.toString
    val s2l: String => Long = _.toLong
    val i2l = i2s >>> s2l
    i2l(10) shouldBe 10L
  }

  "Kleisli" in {
    //val a = Kleisli((v:List[Int]) => v.headOption)
    val lastK = Kleisli((_: List[Int]).lastOption)


  }
}
