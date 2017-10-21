package com.timzaak.di

import com.aliyun.mns.client.{CloudAccount, CloudTopic}
import com.joyrec.util.db.redis.{AutoSelectSingleRedis, AvailableDB, SingleRedisTpl}
import com.timzaak.action._
import com.timzaak.dao.{AccessDao, CommentDao, SmsDao, UserAccountDao}
import com.top10.redis.{Redis, SingleRedis}
import very.util.alisms.{AliMockSmsClient, AliSmsClient}
import very.util.cache.RedisCache

import scala.language.implicitConversions

trait ActionDI extends DaoDI with AkkaDI { di =>
  protected val enableMock: B = conf.getBoolean("mode.mock")

  lazy val redisPool = Redis.pool(Redis.config(40),conf.get[S]("redis.host"),conf.get[I]("redis.port"), conf.getOptional[S]("redis.pwd"),5000)

  case class _RedisDB(db:Int) extends SingleRedis(redisPool) with AutoSelectSingleRedis{
    val select:Int = db
  }

  implicit def availableDBToRedis(v: AvailableDB):_RedisDB = _RedisDB(v.db)


  private object cache extends RedisCache {
    implicit protected def executor = di.executionContext
    protected val redis: SingleRedisTpl = AvailableDB(1)
  }


  object smsAction extends SmsAction {
    override def enableMock: B = di.enableMock

    override protected val smsClient: AliSmsClient = if (di.enableMock) {
      new AliMockSmsClient {}
    } else {
      val aliSmsConf = conf.getConfig("ali.sms")
      val msnClient = new CloudAccount(aliSmsConf.getString("accessId"),
                                       aliSmsConf.getString("accessKey"),
                                       aliSmsConf.getString("endpoint")).getMNSClient
      new AliSmsClient {
        override protected def topic: CloudTopic =
          msnClient.getTopicRef(aliSmsConf.getString("topic"))

        override protected def captchaSignName =
          aliSmsConf.getString("captchaSign")

        override protected def captchaTemplateCode =
          aliSmsConf.getString("captchaTemplate")
      }
    }

    override protected def smsDao: SmsDao = di.smsDao
  }

  val jwtSecretKey = conf.getString("jwt.secret")

  object userAccAction extends UserAccAction {
    override protected def userAccDao: UserAccountDao = di.userAccountDao

    override protected def secretKey: S = jwtSecretKey

    override protected def smsAction: SmsAction = di.smsAction

    override protected val expireTime: I = 60 * 20
  }

  object commentAction extends CommentAction {
    override protected def commentDao: CommentDao = di.commentDao
  }

  object accessAction extends AccessAction {
    override protected def accessDao: AccessDao = di.accessDao
  }
}
