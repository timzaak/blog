package com.timzaak.dao

import com.timzaak.entity.UserAccount
import very.util.db.postgrel.PostgresProfileWithJson4S.api._


class UserAccountTable(tag: Tag) extends Table[UserAccount](tag, "user_account") {
  def id = column[S]("id", O.PrimaryKey, O.AutoInc)

  def account = column[S]("account", O.SqlType("text"))

  def password = column[S]("password")

  def * = (id.?, account, password) <> (UserAccount.tupled, UserAccount.unapply)
}
