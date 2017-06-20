package very.util.flag

import com.typesafe.config.Config

trait ConfigFeatureFlag extends FeatureFlag {
  def getConfig: Config

  override def isEnable(key: String): Boolean = getConfig.getBoolean(key)

}