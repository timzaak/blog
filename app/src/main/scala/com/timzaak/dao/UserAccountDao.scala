package com.timzaak.dao

import com.timzaak.entity.UserAccount
import slick.jdbc.GetResult
import slick.lifted.TableQuery
import very.util.db.postgrel.WithPostgrel
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import scala.concurrent.Future

trait UserAccountDao extends WithPostgrel {
  protected lazy val userAccounts = TableQuery[UserAccountTable]

  private val getByAccAndPwdCompiled = Compiled((account: Rep[String], pwd: Rep[String]) =>
    userAccounts.filter(v => v.account === account && v.password === pwd)
  )

  def getByAccAndPwd(account: S, pwd: S): Future[Option[UserAccount]] = {
    db.run(getByAccAndPwdCompiled(account, pwd).result.headOption)
  }

//  implicit val getUserAccountResult = GetResult(r => UserAccount(r.<<, r.<<, r.<<))
//
//  def getByAccAndPwd2(account: S, pwd: S): Future[Option[UserAccount]] = {
//    db.run(sql"select * from user_account where account=$account and password=$pwd".as[UserAccount].headOption)
//  }
}
