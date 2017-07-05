package com.timzaak.di

import com.timzaak.dao._
import org.pico.hashids.Hashids
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

trait DaoDI extends WithConf { di =>

  implicit val db =
    Database.forURL(url = conf.getString("postgrel.url"), driver = "org.postgresql.Driver")

  //implicit val redisPool = Redis.pool(Redis.config(400), conf.getString("redis.host"), conf.getInt("redis.port"),Option(conf.getString("redis.password")), 5000)

  //  class Redis(db: I) extends SingleRedis(redisPool) with AutoSelectSingleRedis {
  //    val select: Int = db
  //  }
  implicit val hashids = Hashids.reference(conf.getString("server.salt"),4)

  object userAccountDao extends UserAccountDao {}

  object smsDao extends SmsDao {
    override protected val tableName: String = "sms"
  }

  object commentDao extends CommentDao {
    override protected val tableName: String = "comments"
  }

  object accessDao extends AccessDao {
    override protected val tableName: String = "access"
  }

}
