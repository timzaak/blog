package very.util.security

//code from https://github.com/lizepeng/app.io/blob/develop/modules/security/app/security/ModulesAccessControl.scala
//@author zepeng.li@gmail.com

case class Access(self: Long) extends AnyVal {

  def |(that: => Access) = Access(self | that.self)

  def toPermission = Permission(self)

  override def toString = f"${self.toBinaryString}%64s".replace(' ', '0').toString
}

object Access {

  case class Pos(self: Int) extends AnyVal {

    def toAccess = Access(1L << self.min(63).max(0))

    override def toString = self.toString
  }

  object Pos {
    import scala.language.implicitConversions
    implicit def PosToAccess(pos: Pos): Access = pos.toAccess
  }

  val Nothing = Access(0L)

  def union(accesses: Iterable[Access]) = (Nothing /: accesses) (_ | _)
}