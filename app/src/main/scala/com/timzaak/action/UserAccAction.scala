package com.timzaak.action

import com.timzaak.dao.UserAccountDao

abstract class UserAccAction {
  def userAccDao: UserAccountDao

  protected[action] def secretPwd(acc: S, pwd: S) = {
    acc + pwd
  }


}
