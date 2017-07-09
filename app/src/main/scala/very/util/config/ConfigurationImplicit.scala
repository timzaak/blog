package very.util.config

import com.typesafe.config._
import scala.concurrent.duration._

//All the code is from playframework
trait ConfigLoader[A] {
  self =>
  def load(config: Config, path: String = ""): A

  def map[B](f: A => B): ConfigLoader[B] = new ConfigLoader[B] {
    def load(config: Config, path: String): B = {
      f(self.load(config, path))
    }
  }
}


object ConfigLoader {

  def apply[A](f: Config => String => A): ConfigLoader[A] = new ConfigLoader[A] {
    def load(config: Config, path: String): A = f(config)(path)
  }

  import scala.collection.JavaConverters._

  implicit val stringLoader: ConfigLoader[String] = ConfigLoader(_.getString)
  implicit val seqStringLoader: ConfigLoader[Seq[String]] = ConfigLoader(_.getStringList).map(_.asScala)

  implicit val intLoader: ConfigLoader[Int] = ConfigLoader(_.getInt)
  implicit val seqIntLoader: ConfigLoader[Seq[Int]] = ConfigLoader(_.getIntList).map(_.asScala.map(_.toInt))

  implicit val booleanLoader: ConfigLoader[Boolean] = ConfigLoader(_.getBoolean)
  implicit val seqBooleanLoader: ConfigLoader[Seq[Boolean]] =
    ConfigLoader(_.getBooleanList).map(_.asScala.map(_.booleanValue))

  implicit val durationLoader: ConfigLoader[Duration] = ConfigLoader { config =>
    path =>
      if (!config.getIsNull(path)) config.getDuration(path).toNanos.nanos else Duration.Inf
  }

  // Note: this does not support null values but it added for convenience
  implicit val seqDurationLoader: ConfigLoader[Seq[Duration]] =
    ConfigLoader(_.getDurationList).map(_.asScala.map(_.toNanos.nanos))

  implicit val finiteDurationLoader: ConfigLoader[FiniteDuration] =
    ConfigLoader(_.getDuration).map(_.toNanos.nanos)
  implicit val seqFiniteDurationLoader: ConfigLoader[Seq[FiniteDuration]] =
    ConfigLoader(_.getDurationList).map(_.asScala.map(_.toNanos.nanos))

  implicit val doubleLoader: ConfigLoader[Double] = ConfigLoader(_.getDouble)
  implicit val seqDoubleLoader: ConfigLoader[Seq[Double]] =
    ConfigLoader(_.getDoubleList).map(_.asScala.map(_.doubleValue))

  implicit val numberLoader: ConfigLoader[Number] = ConfigLoader(_.getNumber)
  implicit val seqNumberLoader: ConfigLoader[Seq[Number]] = ConfigLoader(_.getNumberList).map(_.asScala)

  implicit val longLoader: ConfigLoader[Long] = ConfigLoader(_.getLong)
  implicit val seqLongLoader: ConfigLoader[Seq[Long]] =
    ConfigLoader(_.getDoubleList).map(_.asScala.map(_.longValue))

  implicit val bytesLoader: ConfigLoader[ConfigMemorySize] = ConfigLoader(_.getMemorySize)
  implicit val seqBytesLoader: ConfigLoader[Seq[ConfigMemorySize]] = ConfigLoader(_.getMemorySizeList).map(_.asScala)

  implicit val configLoader: ConfigLoader[Config] = ConfigLoader(_.getConfig)
  implicit val configListLoader: ConfigLoader[ConfigList] = ConfigLoader(_.getList)
  implicit val configObjectLoader: ConfigLoader[ConfigObject] = ConfigLoader(_.getObject)
  implicit val seqConfigLoader: ConfigLoader[Seq[Config]] = ConfigLoader(_.getConfigList).map(_.asScala)

  //  implicit val configurationLoader: ConfigLoader[Configuration] = configLoader.map(Configuration(_))
  //  implicit val seqConfigurationLoader: ConfigLoader[Seq[Configuration]] = seqConfigLoader.map(_.map(Configuration(_)))
  //
  //  private[play] implicit val playConfigLoader: ConfigLoader[PlayConfig] = configLoader.map(PlayConfig(_))
  //  private[play] implicit val seqPlayConfigLoader: ConfigLoader[Seq[PlayConfig]] = seqConfigLoader.map(_.map(PlayConfig(_)))

  /**
    * Loads a value, interpreting a null value as None and any other value as Some(value).
    */
  implicit def optionLoader[A](implicit valueLoader: ConfigLoader[A]): ConfigLoader[Option[A]] = new ConfigLoader[Option[A]] {
    def load(config: Config, path: String): Option[A] = {
      if (config.getIsNull(path)) None else {
        val value = valueLoader.load(config, path)
        Some(value)
      }
    }
  }

  implicit def mapLoader[A](implicit valueLoader: ConfigLoader[A]): ConfigLoader[Map[String, A]] = new ConfigLoader[Map[String, A]] {
    def load(config: Config, path: String): Map[String, A] = {
      val obj = config.getObject(path)
      val conf = obj.toConfig
      obj.keySet().asScala.map { key =>
        key -> valueLoader.load(conf, key)
      }.toMap
    }
  }
}


trait ConfigurationImplicit {

  implicit class Configuration(underlying: Config) {


    private def readValue[T](path: String, v: => T): Option[T] = {
      if (underlying.hasPathOrNull(path)) Some(v) else None
    }

    def get[A](path: String)(implicit loader: ConfigLoader[A]): A = {
      loader.load(underlying, path)
    }

    def getOptional[A](path: String)(implicit loader: ConfigLoader[A]): Option[A] = {
      readValue(path, get[A](path))
    }

  }
}