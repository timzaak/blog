package com.timzaak.chaos


import org.scalatest.{FreeSpec, Matchers}

class SelfTypeTest extends FreeSpec with Matchers{
  trait Abstract0[+Self <: Abstract0[_]] {
    def get:this.type = this
    def getList:List[Self] = List.empty
  }

  class Concrete0 extends Abstract0[Concrete0] {}

  class Concrete1 extends Abstract0[Concrete1] {}


  "Self1 with Abstract0 can work" in {
    val c0 = new Concrete0().get
    val c1 = new Concrete1().get
    val c2 = (c0.getList ::: c0.getList):List[Concrete0]
    //val c3: Iterable[Abstract0[Any]] = c0.getList:::c1.getList

    assert(true, "bad one")
  }

}
