package com.timzaak.action

import com.timzaak.dao.SmsDao
import ws.very.util.lang.Mockable

import scala.concurrent.{Future, Promise}

trait SmsAction extends Action with Mockable {
  protected def smsDao: SmsDao

  def getCaptcha(mobile: S) = smsDao.getCaptcha(mobile)

  def sendCaptcha(mobile: S, captcha: S): Future[U] = mock2Default {
    Promise.successful[U](()).future
  } {
    Promise.successful[U](()).future
  }.map { _ =>
    smsDao.saveCaptcha(mobile, captcha)
  }

}
