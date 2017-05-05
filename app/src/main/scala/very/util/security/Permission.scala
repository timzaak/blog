package very.util.security

//code from https://github.com/lizepeng/app.io/blob/develop/modules/security/app/security/ModulesAccessControl.scala
//@author zepeng.li@gmail.com

case class Permission(self: Long) extends AnyVal {

  def |(that: => Permission) = Permission(self | that.self)

  def ?(access: => Access) = (self & access.self) == access.self

  override def toString = f"${self.toBinaryString}%64s".replace(' ', '0')
}

object Permission {

  val Anything = Permission(0xFFFFFFFFFFFFFFFFL)
  val Nothing = Permission(0x0000000000000000L)

  def union(permissions: Permission*) = (Nothing /: permissions) (_ | _)
}

trait PermissionCheckable {
  def resource: String

  def AccessDef: BasicAccessDef

}