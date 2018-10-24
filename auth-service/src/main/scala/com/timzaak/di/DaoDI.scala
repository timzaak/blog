package com.timzaak.di

import com.timzaak.dao.{AuthTokenDao, OAuthAuthorizationDao, OAuthClientDao, UserDao}
import scalikejdbc.{AutoSession, DBSession}
import scalikejdbc.config.DBs

trait DaoDI {
  DBs.setupAll()

  implicit val session:DBSession = AutoSession

  object userDao extends UserDao

  object authTokenDao extends AuthTokenDao

  object authAuthorizationDao extends OAuthAuthorizationDao

  object authClientDao extends OAuthClientDao
}
