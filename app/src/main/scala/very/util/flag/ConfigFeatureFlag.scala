package very.util.flag

import com.typesafe.config.Config

trait ConfigFeatureFlag extends FeatureFlag {
  protected def getConfig: Config

  override def isEnable(key: String): Boolean = getConfig.getBoolean(key)

  override def variable(key: String): String = getConfig.getString(key)
}