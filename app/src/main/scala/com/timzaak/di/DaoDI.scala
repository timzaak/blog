package com.timzaak.di

import com.timzaak.dao._
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

trait DaoDI extends WithConf{
  di =>

  implicit val db = Database.forURL(
    url = conf.getString("postgrel.url"),
    driver = "org.postgresql.Driver")


  //implicit val redisPool = Redis.pool(Redis.config(400), conf.getString("redis.host"), conf.getInt("redis.port"),Option(conf.getString("redis.password")), 5000)

//  class Redis(db: I) extends SingleRedis(redisPool) with AutoSelectSingleRedis {
//    val select: Int = db
//  }

  object userAccountDao extends UserAccountDao{
  }

  object smsDao extends SmsDao {
    override protected val tableName: String = "sms"
  }

  object commentDao extends CommentDao
}
