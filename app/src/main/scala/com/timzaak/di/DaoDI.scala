package com.timzaak.di

import com.joyrec.util.db.redis.{AutoSelectSingleRedis, SingleRedisTpl}
import com.timzaak.dao._
import com.top10.redis.{Redis, SingleRedis}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

trait DaoDI extends AkkaDI {
  di =>

  private def password = "timzaak"

  private def user = "timzaak"

  implicit val db = Database.forURL(
    url = s"jdbc:postgresql://localhost:5432/postgres?user=${user}&password=${password}",
    driver = "org.postgresql.Driver")

  implicit val redisPool = Redis.pool(Redis.config(400), "localhost", 6666, Some("ilt"), 5000)

  class Redis(db: I) extends SingleRedis(redisPool) with AutoSelectSingleRedis {
    val select: Int = db
  }

  object userAccountDao extends UserAccountDao{
    override protected def redis: SingleRedisTpl = new Redis(2)
  }

  object smsDao extends SmsDao {
    override protected def expireTime: I = 120

    override protected def redis: SingleRedisTpl = new Redis(1)
  }

}
