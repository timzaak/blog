package very.util.flag

trait FeatureFlag {
  def isEnable(key: String): Boolean

  def isDisabled(key: String): Boolean = !isEnable(key)

  def flag[T](key: String)(enableFn: => T)(disableFn: => T): T = {
    if (isEnable(key)) {
      enableFn
    } else {
      disableFn
    }
  }

  def variable(key: String): String

  //may be we need js to solve dynamic logic
}
