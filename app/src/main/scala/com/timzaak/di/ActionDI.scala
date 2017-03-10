package com.timzaak.di

import com.timzaak.action.UserAccAction
import com.timzaak.dao.UserAccountDao

trait ActionDI extends DaoDI { di =>

  object userAccAction extends UserAccAction {
    override def userAccDao: UserAccountDao = di.userAccountDao
  }

}
