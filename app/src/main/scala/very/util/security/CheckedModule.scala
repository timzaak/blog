package very.util.security

//code from https://github.com/lizepeng/app.io/blob/develop/modules/security/app/security/ModulesAccessControl.scala
//@author zepeng.li@gmail.com
case class CheckedModule(name: String) extends AnyVal {
  override def toString = name
}