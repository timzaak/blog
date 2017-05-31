package com.timzaak.di

import com.aliyun.mns.client.{ CloudAccount, CloudTopic }
import com.timzaak.action._
import com.timzaak.dao.{ AccessDao, CommentDao, SmsDao, UserAccountDao }
import very.util.alisms.{ AliMockSmsClient, AliSmsClient }

trait ActionDI extends DaoDI with AkkaDI { di =>
  val enableMock: B = conf.getBoolean("mode.mock")

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

  val jwtSecretKey = conf.getString("jwt.secrete")

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
    override def accessDao: AccessDao = di.accessDao
  }

}
