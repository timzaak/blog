package com.timzaak.action

import com.timzaak.dao.SmsDao
import very.util.alisms.AliSmsClient
import ws.very.util.lang.Mockable

import scala.concurrent.Future
import scala.util.{Random, Success}

trait SmsAction extends Action with Mockable {
  protected def smsDao: SmsDao
  protected def smsClient: AliSmsClient

  protected def captchaCode(): S = {
    val captcha = Random.nextInt(99999).toString
    if (captcha.length < 4) {
      captchaCode()
    } else {
      captcha
    }
  }

  def getCaptcha(mobile: S) = smsDao.getCaptcha(mobile)

  def sendCaptcha(mobile: S): Future[S] = {
    val code = captchaCode()
    mock2Default {
      smsClient.sendCaptchaSms("code" -> code)(mobile)
    } {
      Success("TestId")
    }.map { resultId =>
      smsDao.saveCaptcha(mobile, code, resultId).map(_ => code)
    }
  }
}
