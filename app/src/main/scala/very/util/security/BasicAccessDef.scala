package very.util.security

//code from https://github.com/lizepeng/app.io/blob/develop/modules/security/app/security/ModulesAccessControl.scala
//@author zepeng.li@gmail.com

trait BasicAccessDef {

  def Pos(pos: Int) = Access(1L << pos.min(63).max(0))

  /** Access : New */
  val P00 = Access.Pos(0)

  /** Access : Create */
  val P01 = Access.Pos(1)

  /** Access : Show */
  val P02 = Access.Pos(2)

  /** Access : Index */
  val P03 = Access.Pos(3)

  /** Access : Edit */
  val P04 = Access.Pos(4)

  /** Access : Save */
  val P05 = Access.Pos(5)

  /** Access : Destroy */
  val P06 = Access.Pos(6)

  /** Access : Index History */
  val P07 = Access.Pos(7)
  // 8 ~ 15 are preserved

  def values: Seq[Access.Pos]

  def permission: Permission =
    Access.union(values.map(_.toAccess)).toPermission
}
