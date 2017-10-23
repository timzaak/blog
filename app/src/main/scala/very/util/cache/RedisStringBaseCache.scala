package very.util.cache

import com.joyrec.util.db.redis.WithRedis

import scala.concurrent.duration.Duration
import scala.concurrent.{ ExecutionContext, Future }
import scalacache.Cache
import scalacache.serialization.Codec

trait RedisStringBaseCache extends Cache[String] with WithRedis with PrimaryStringCodec {
  implicit protected def executor: ExecutionContext

  implicit private def codec[V <: AnyRef: Manifest] = new JsonCodec[V]

  override def get[V](key: String)(implicit codec: Codec[V, String]): Future[Option[V]] = {
    Future {
      redis.get(key).map(codec.deserialize _)
    }
  }

  override def put[V](key: String, value: V, ttl: Option[Duration])(
      implicit codec: Codec[V, String]
  ): Future[Any] = {
    Future {
      redis.set(key, codec.serialize(value))
      ttl.foreach(t => redis.expire(key, t.toSeconds.toInt))
    }
  }

  override def remove(key: String): Future[Any] = {
    Future {
      redis.del(key)
    }
  }

  override def removeAll(): Future[Any] = {
    Future {
      redis.flushDB
    }
  }

  override def close(): Unit = redis.shutdown
}
