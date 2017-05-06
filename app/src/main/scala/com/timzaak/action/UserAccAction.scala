package com.timzaak.action

import java.time._

import com.timzaak.dao.UserAccountDao
import com.timzaak.entity.UserAccount
import very.util.security.JwtAuthEncode
import ws.very.util.lang.Texts
import ws.very.util.security.SHA

import scala.concurrent.Future

trait UserAccAction extends Action with JwtAuthEncode {

  protected def userAccDao: UserAccountDao

  protected def smsAction: SmsAction

  protected def expireTime: I

  private def secretPwd(acc: S, pwd: S) = {
    SHA(acc + pwd, SHA.SHA_256)
  }


  def login(acc: MobileNum, pwd: S) = {
    userAccDao.getByAccAndPwd(acc, secretPwd(acc, pwd)).map(_.flatMap(_.id)).map {
      case Some(userId) =>
        jwtEncode(userId.toString)
      case None =>
        throw new IllegalArgumentException("账号或密码不能存在")
    }
  }

  def sampleRegister(acc: S, pwd: S): Future[S] = {
    userAccDao.newAcc(UserAccount(None, acc, secretPwd(acc, pwd))).map { id =>
      jwtEncode(id.toString)
    }
  }

  def checkMobileExists(mobile: MobileNum):Future[Option[L]] = {
    userAccDao.getAccId(mobile)
  }

  def getRegisterCaptcha(mobile: MobileNum):Future[S] = {
    if (mobile.matches(Texts.Regex.Num)) {
      smsAction.getCaptcha(mobile).flatMap {
        case Some((_, time)) if Duration.between(time, LocalDateTime.now()).getSeconds < 60 =>
            new IllegalArgumentException("1分钟内不能多次请求")
        case _ =>
          smsAction.sendCaptcha(mobile)
      }
    }else{
      new IllegalArgumentException("手机号格式错误")
    }
  }

  def register(mobile: MobileNum, pwd: S, capture: Captcha): Future[S] = {
    smsAction.getCaptcha(mobile).flatMap {
      case Some((code, time)) if Duration.between(time, LocalDateTime.now()).getSeconds < expireTime && code == capture =>
        userAccDao.newAcc(UserAccount(None, mobile, secretPwd(mobile, pwd))).recoverWith {
          case _ => Future.failed(new IllegalArgumentException("账号已存在"))
        }.map(userId => jwtEncode(userId.toString))
      case _ =>
        new IllegalArgumentException("验证码错误")
    }
  }

}
