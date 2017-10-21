package very.util.cache

import scalacache.ScalaCache

trait RedisCache extends RedisStringBaseCache{
  implicit def toScalaCache:ScalaCache[String] = ScalaCache(this)
}