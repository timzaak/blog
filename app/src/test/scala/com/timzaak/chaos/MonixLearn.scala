package com.timzaak.chaos

import java.util.concurrent.TimeUnit

import monix.eval._
import monix.execution.Ack.Continue
import monix.execution._
import monix.execution.cancelables.{BooleanCancelable, CompositeCancelable, MultiAssignCancelable, SingleAssignCancelable}
import org.scalatest._
import monix.execution.schedulers.TestScheduler
import monix.reactive._
import monix.reactive.observers._

import scala.concurrent.Future
import scala.util.Success
import scala.concurrent.duration._
// sbt "testOnly com.timzaak.chaos.MonixLearn"
class MonixLearn extends FreeSpec with Matchers{
//  “scala.concurrent.context.minThreads” an integer specifying the minimum number of active threads in the pool
//  “scala.concurrent.context.maxThreads” an integer specifying the maximum number of active threads in the pool
//  “scala.concurrent.context.numThreads” can be either an integer, specifying the parallelism directly or a string with the format “xNUM” (e.g. “x1.5”) specifying the multiplication factor of the number of available processors
implicit val testScheduler = TestScheduler()
  "task" in {


    val task = Task(1+1)

    val result = task.runAsync(testScheduler)
    result.value shouldBe empty
    testScheduler.tick(1.seconds)
    result.value shouldBe defined
  }
  "scheduler" in {
    var a = false
    testScheduler.scheduleOnce(1 ,TimeUnit.SECONDS, () => a = true)
    testScheduler.tick(2.seconds)
    a shouldBe true
  }
  "Cancel" - {
    "boolean" in {
      val c = BooleanCancelable.alreadyCanceled
      c.isCanceled shouldBe true
      val c2 = BooleanCancelable()
      c2.isCanceled shouldBe false
      c2.cancel()
      c2.isCanceled shouldBe true
    }

    "composite" in {
      val c = CompositeCancelable()
      var a1,a2,a3 = false
      c += Cancelable(() => a1 = true)
      c += Cancelable(() => a2 = true)
      c.cancel()
      c += Cancelable(() => a3 = true)
      Seq(a1,a2,a3).contains(false) shouldBe false
    }

    "multiAssignment" in {
      val c = MultiAssignCancelable()
      var a1,a2,a3 = false
      c := Cancelable(() => a1 = true)
      c := Cancelable(() => a2 = true)
      c.cancel()
      c := Cancelable(() => a3 = true)
      List(a1,a2,a3) shouldBe List(false, true, true)
    }
    "SingleAssignment" in {
      //val c = SingleAssignCancelable()
      var a1,a2,a3 = false
      val c = SingleAssignCancelable.plusOne(Cancelable(() => a1 = true))
      c := Cancelable(() => a2 = true)
      c.cancel()
      //c := Cancelable(() => a3 = true)
      List(a1,a2,a3) shouldBe List(true, true, false)
    }
  }

  "mvar" in {
    def sum(state:MVar[Int], list:List[Int]):Task[Int] = {
      list match {
        case Nil => state.take
        case x::xs =>
          state.take.flatMap{ current =>
            state.put(current + x).flatMap(_ => sum(state, xs))
          }
      }
    }

    val state =MVar(1)
    val task = sum(state, (0 until 100).toList)
    val result =task.runAsync(testScheduler)
    testScheduler.tick(2.seconds)
    result.value shouldBe Some(Success(4951))
  }

  "Observable" in {
    val source = Observable.interval(1.seconds)
      .filter(_%2 ==0)
      .flatMap(x => Observable(x,x))
      .take(10)
    source.dump("0").subscribe()(testScheduler)
    testScheduler.tick(20.seconds)
    1 shouldBe 1
  }

  "Subscribe" - {
//    "simple" in {
//      new Observer[Any]{
//        override def onNext(elem: Any): Future[Ack] = {
//          println(s"()-->$elem")
//          Continue
//        }
//
//        override def onError(ex: Throwable): U = {
//          ex.printStackTrace()
//        }
//
//        override def onComplete(): U = {
//          println("0 finished")
//        }
//      }
//
//    }

    "dump" in {
      val out = Observer.dump("0")
      out.onNext(1)
      out.onNext(2)
      out.onComplete()

      Subscriber.dump("0")
      1 shouldBe 1
    }

    "buffer" in {
      val s = Subscriber.dump("0")
      val subscriber = BufferedSubscriber(s,OverflowStrategy.DropOld(2))

    }
  }

  "consumer" in {

    val sumConsumer = Consumer.foldLeft[Long,Long](0L)(_ + _)
    val loadBalancer =
      Consumer.loadBalance(parallelism = 10, sumConsumer).map{v=>
        v.sum
      }
    val observable = Observable.range(0,10)
    val task = observable.consumeWith(loadBalancer)
    val result = task.runAsync(testScheduler)
    testScheduler.tick(2.seconds)
    result.value shouldBe Some(Success(45))

  }
}
