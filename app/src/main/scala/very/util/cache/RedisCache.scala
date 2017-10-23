package very.util.cache

import scalacache.ScalaCache

trait RedisCache extends RedisStringBaseCache {}

object RedisCache {
  import scala.language.implicitConversions
  implicit def toScalaCache(r: RedisCache): ScalaCache[String] = ScalaCache(r)
}
