package com.timzaak.di

import com.timzaak.dao.UserDao
import scalikejdbc.{AutoSession, DBSession}
import scalikejdbc.config.DBs

trait DaoDI {
  DBs.setupAll()

  implicit val session:DBSession = AutoSession

  object userDao extends UserDao

}
