package test

// sbt "runMain test.KeyllyFormula 50000 0.2 5"
object KeyllyFormula {
  def main(args: Array[String]): U = {
    val Array(money, p, b) = args.map(_.toDouble)
//    f = 应该放入投注的资本比值
//    p = 获胜的概率
//    b = 赔率
    println(money, p, b)
    val f = (p*(b+1)-1)/b
    println(s"f*: $f")
    println(s"money: ${money*f}")
  }
}
