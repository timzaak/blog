package very.util.config

import com.typesafe.config.Config

trait WithConf {
  def conf: Config
}
