package com.timzaak.di

import com.joyrec.util.db.redis.{AutoSelectSingleRedis, SingleRedisTpl}
import com.timzaak.dao._
import com.timzaak.generated.{Tables, UsersRow}
import com.top10.redis.{Redis, SingleRedis}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

trait DaoDI extends AkkaDI with WithConf{
  di =>

  implicit val db = Database.forURL(
    url = conf.getString("postgrel.url"),
    driver = "org.postgresql.Driver")


  implicit val redisPool = Redis.pool(Redis.config(400), conf.getString("redis.host"), conf.getInt("redis.port"),Option(conf.getString("redis.password")), 5000)

  class Redis(db: I) extends SingleRedis(redisPool) with AutoSelectSingleRedis {
    val select: Int = db
  }
import Tables._
  UsersRow
  object userAccountDao extends UserAccountDao{
    override protected def redis: SingleRedisTpl = new Redis(2)
  }

  object smsDao extends SmsDao {
    override protected def expireTime: I = 120

    override protected def redis: SingleRedisTpl = new Redis(1)
  }

}
