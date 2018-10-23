package com.timzaak.di

import com.timzaak.dao.{AuthTokenDao, UserDao}
import com.timzaak.oauth.{ImplicitAuthorization, OAuthDataProvider}
import com.timzaak.service.OAuthService

import scala.concurrent.ExecutionContext

trait AuthDI extends DaoDI{di =>
  implicit def ec: ExecutionContext
  object oAuthService extends OAuthService {
    override def userDao: UserDao = di.userDao
    override def authTokenDao: AuthTokenDao = di.authTokenDao
  }



  object ImplicitAuthorization extends ImplicitAuthorization {
    override implicit def ec: ExecutionContext = di.ec

    override def EnableRedirectAnyUrl: Boolean = true

    override def provider: OAuthDataProvider = oAuthService
  }
}
