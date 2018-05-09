package com.timzaak.chaos

import java.util.concurrent.TimeUnit

import monix.eval._
import cats.implicits._
import monix.execution.Ack.Continue
import monix.execution._
import monix.execution.atomic.Atomic
import monix.execution.cancelables._
import org.scalatest._
import monix.execution.schedulers.TestScheduler
import monix.reactive._
import monix.reactive.observers._

import scala.concurrent.Future
import scala.util.Success
import scala.concurrent.duration._

// sbt "testOnly com.timzaak.chaos.MonixLearn"
class MonixLearn extends FreeSpec with Matchers {
  //  “scala.concurrent.context.minThreads” an integer specifying the minimum number of active threads in the pool
  //  “scala.concurrent.context.maxThreads” an integer specifying the maximum number of active threads in the pool
  //  “scala.concurrent.context.numThreads” can be either an integer, specifying the parallelism directly or a string with the format “xNUM” (e.g. “x1.5”) specifying the multiplication factor of the number of available processors

  // Observable 是对可观察的持续性(数据源/事件)的封装
  // Observer 是具体的观察者，而实际运行时，需要用Scheduler驱动观察者，也就是 Subscriber
  // Consumer 是拿观察到的数据具体去干什么， 和 Subscriber 解耦

  // RefCountCancelable 可以将引用给其它 Cancel，当其它全挂了，它才挂。

  implicit val testScheduler = TestScheduler()
  "task" ignore {


    val task = Task(1 + 1)

    val result = task.runAsync(testScheduler)
    result.value shouldBe empty
    testScheduler.tick(1.seconds)
    result.value shouldBe defined
  }
  "scheduler" ignore {
    var a = false
    testScheduler.scheduleOnce(1, TimeUnit.SECONDS, () => a = true)
    testScheduler.tick(2.seconds)
    a shouldBe true
  }
  "Cancel" - {
    "boolean" ignore {
      val c = BooleanCancelable.alreadyCanceled
      c.isCanceled shouldBe true
      val c2 = BooleanCancelable()
      c2.isCanceled shouldBe false
      c2.cancel()
      c2.isCanceled shouldBe true
    }

    "composite" in {
      val c = CompositeCancelable()
      var a1, a2, a3 = false
      c += Cancelable(() => a1 = true)
      c += Cancelable(() => a2 = true)
      c.cancel()
      c += Cancelable(() => a3 = true)
      Seq(a1, a2, a3).contains(false) shouldBe false
    }

    "composite2" ignore {
      var a1, a2,all = false
      // composite will not trigger init cancel when all children cancel
      val c = CompositeCancelable(()=> all = true)
      val Array(c1,c2) = Array(Cancelable(()=>a1= !a1),Cancelable(()=> a2= !a2))
      c += c1
      c += c2
      c1.cancel()
      c2.cancel()
      Seq(a1, a2, all).contains(false) shouldBe true
    }

    "multiAssignment" ignore {
      // multi one
      val c = MultiAssignCancelable()
      var a1, a2, a3 = false
      c := Cancelable(() => a1 = true)
      c := Cancelable(() => a2 = true)
      c.cancel()
      c := Cancelable(() => a3 = true)
      List(a1, a2, a3) shouldBe List(false, true, true)
    }

    "multiAssignment2" ignore {
      var a,a1, a2 = false
      val c = MultiAssignCancelable{ () =>
        println("a go to true")
        a = true
      }

      val c1 =  Cancelable{() =>
        println("a1 go to true")
        a1 = true
      }
      c := c1
      val c2 =  Cancelable{() =>
        println("a2 go to true")
        a2 = true
      }
      c := c2
      c2.cancel()
      c.isCanceled shouldBe false

      a2 shouldBe true
      a1 shouldBe false
      c1 cancel()
      a1 shouldBe true
      c.isCanceled shouldBe false
      a shouldBe false

    }
    "SingleAssignment" ignore {
      //val c = SingleAssignCancelable()
      var a1, a2, a3 = false
      val c = SingleAssignCancelable.plusOne(Cancelable(() => a1 = true))
      c := Cancelable(() => a2 = true)
      c.cancel()
      //c := Cancelable(() => a3 = true)
      List(a1, a2, a3) shouldBe List(true, true, false)
    }

    "refCount" in {
      val refs = RefCountCancelable { () =>
        println("Everything was canceled")
      }

    }
  }

  "mvar" ignore {
    def sum(state: MVar[Int], list: List[Int]): Task[Int] = {
      list match {
        case Nil => state.take
        case x :: xs =>
          state.take.flatMap { current =>
            state.put(current + x).flatMap(_ => sum(state, xs))
          }
      }
    }

    val state = MVar(1)
    val task = sum(state, (0 until 100).toList)
    val result = task.runAsync(testScheduler)
    testScheduler.tick(2.seconds)
    result.value shouldBe Some(Success(4951))
  }

  "Observable" ignore {
    val source = Observable.interval(1.seconds)
      .filter(_ % 2 == 0)
      .flatMap(x => Observable(x, x))
      .take(10)
    source.dump("0").subscribe()(testScheduler)
    testScheduler.tick(20.seconds)
    1 shouldBe 1
  }

  "Subscribe" - {
    "dump" ignore {
      val out = Observer.dump("0")
      out.onNext(1)
      out.onNext(2)
      out.onComplete()

      Subscriber.dump("0")
      1 shouldBe 1
    }

    "buffer" ignore {
      val s = Subscriber.dump("0")
      val subscriber = BufferedSubscriber(s, OverflowStrategy.DropOld(2))

    }
  }

  "consumer" ignore {

    val sumConsumer = Consumer.foldLeft[Long, Long](0L)(_ + _)
    val loadBalancer =
      Consumer.loadBalance(parallelism = 10, sumConsumer).map { v =>
        v.sum
      }
    val observable = Observable.range(0, 10)
    val task = observable.consumeWith(loadBalancer)
    val result = task.runAsync(testScheduler)
    testScheduler.tick(2.seconds)
    result.value shouldBe Some(Success(45))
  }

  "one observable with more consumers" ignore {
    val atom = Atomic(0L)
    val data = Observable.interval(1.seconds).take(3)
    val c = Consumer.foreach[Long]{v =>
      println("c1",v)
      atom.increment(v.toInt)}
    val c2 = Consumer.foreach[Long]{v =>
      println("c2",v)
      atom.increment(v.toInt*2)
    }
    data.consumeWith(c).runAsync(testScheduler)
    data.consumeWith(c2).runAsync(testScheduler)
    testScheduler.tick(11.seconds)
    atom.get shouldBe 9
  }

  "mix Observable and Consumer" ignore {
    val a = Atomic(0)

    val _subscriber = new Subscriber[Int] {
      override implicit def scheduler: Scheduler = testScheduler

      override def onNext(elem: I): Future[Ack] = {
        Task {
          a.add(elem)
        }.delayResult(2.seconds).runAsync(testScheduler).map { _ =>
          Continue
        }
      }

      override def onError(ex: Throwable): U = {

      }

      override def onComplete(): U = {

      }
    }

    //val subscribe = BufferedSubscriber(SafeSubscriber(_subscriber), OverflowStrategy.DropOld(2))

    //Observable.empty[Int].subscribe(subscribe)
    //Consumer.fromObserver(_ => subscribe)
  }

//  "buffer" in {
//    Observable.fromIterable(0 to 10).bufferSliding(10,1).foreach(println(_))(testScheduler)
//    1 shouldBe 1
//  }

  "combineLatest" in {
    //val a = Observable.interval(1.seconds).take(5).map(v => s"a$v")
    //val b = Observable.interval(2.seconds).take(5).map(v => s"b$v")
//    (a1,b0)
//    (a2,b0)
//    (a2,b1)
//    (a3,b1)
//    (a4,b1)
//    (a4,b2)
//    (a4,b3)
//    (a4,b4)

//    a.combineLatest(b).foreach(println(_))

//    (b0,a0)
//    (b0,a1)
//    (b0,a2)
//    (b1,a2)
//    (b1,a3)
//    (b1,a4)
//    (b2,a4)
//    (b3,a4)
//    (b4,a4)

//    b.combineLatest(a).foreach(println(_))

//    (a0,b0)
//    (a1,b1)
//    (a2,b2)
//    (a3,b3)
//    (a4,b4)
//    a.zip(b).foreach(println(_))

    val a = Observable.interval(1.seconds).take(5).map(v => s"a$v")
    val b = Observable.interval(2.seconds).take(4).map(v => s"b$v")
    a.combineLatest(b).distinctUntilChangedByKey(v => v._1).foreach(println(_))
    testScheduler.tick(20.seconds)

    //Observable.fromIterable(1 to 10).foldLeftF(0)(_+_).foreach{_ => println("111")}
    //Observable.fromIterable(1 to 10).foldLeftF()

  }
}
