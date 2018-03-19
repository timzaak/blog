package test

import scala.util.Random
//sbt "runMain test.PercentWin"
object PercentWin extends App{
  // 连续三次失败后，All in
  for {
    j <- 1 to 100
  } {
    var mount: Int = 1000
    var count = 0
    for {
      i <- 1 to 100
    } {
      if (mount < 2) {

      } else {
        if (Random.nextInt(10) < 6) {
          if (count == 3) {
            mount = mount * 2
          }
          mount = (mount * 0.1).toInt + mount
          count = 0
        } else {
          if (count == 3) {
            mount = 0
          }
          mount = mount - (mount * 0.1).toInt
          count = count + 1

        }
      }

    }
    println(s"$j : $mount")
  }

}
