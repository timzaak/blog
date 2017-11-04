package very.util.cache

import com.joyrec.util.db.redis.WithRedis
import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}
import scalacache.Cache
import scalacache.serialization.Codec


/** 存在性能优化空间
  * 1. 多次 String 复制转换
  * 2. Avro 存在 output input 转移
  * 3. 需要 Redis Bytes 级别的接口
  * 4. Avro 使用 Bytes 级别的转换
  */
trait RedisStringBaseCache extends Cache[String] with WithRedis with PrimaryStringCodec {
  implicit protected def executor: ExecutionContext

  //implicit private def codec[V <: AnyRef: Manifest] = new Json4SCodec[V]

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
