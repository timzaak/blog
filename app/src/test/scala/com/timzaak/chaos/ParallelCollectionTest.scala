package com.timzaak.chaos

import java.util.concurrent.ForkJoinPool

import org.scalatest._
import scala.collection.parallel.ForkJoinTaskSupport

//sbt "testOnly com.timzaak.chaos.ParallelCollectionTest"
class ParallelCollectionTest extends FreeSpec with Matchers{
  //par 执行都是二分法到最细粒度做的，如果需要粗粒度，需要在执行前自行 grouped 定义
  "first example" in {
    val pc = (1 to 1000).grouped(3).toVector.par
    pc.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(3))
    pc.foreach(println)
    1 shouldBe 1

  }
}
