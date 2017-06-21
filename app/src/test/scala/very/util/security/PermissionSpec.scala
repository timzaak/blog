package very.util.security

import org.scalatest.{ FreeSpec, Matchers }

class PermissionSpec extends FreeSpec with Matchers {

  "permission" - {
    "could correctly join and check" in {
      val basicAccess = new BasicAccessDef {
        override def values: Seq[Access.Pos] = Seq(P00, P01, P02)
      }

      basicAccess.permission ? Access.Pos(0) shouldBe true
      basicAccess.permission ? Access.union(basicAccess.P01, basicAccess.P02) shouldBe true
      basicAccess.permission ? Access.union(basicAccess.P01, basicAccess.P04) shouldBe false
    }
  }
}
