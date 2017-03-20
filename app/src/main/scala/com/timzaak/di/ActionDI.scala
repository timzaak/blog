package com.timzaak.di

import com.timzaak.action._
import com.timzaak.dao.UserAccountDao

trait ActionDI extends DaoDI {di =>
  val enableMock: B = true

  object smsAction extends SmsAction {
    override def enableMock: B = di.enableMock
  }

  object userAccAction extends UserAccAction {
    override protected def userAccDao: UserAccountDao = di.userAccountDao

    override protected def secretKey: S = "guess_what_the_secreat"

    override protected def smsAction: SmsAction = di.smsAction
  }

}
