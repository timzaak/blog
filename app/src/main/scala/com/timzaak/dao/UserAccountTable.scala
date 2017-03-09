package com.timzaak.dao

import com.timzaak.entity.UserAccount
import very.util.db.postgrel.PostgresProfileWithJson4S.api._


class UserAccountTable(tag: Tag) extends Table[UserAccount](tag, "user_account") {
  def id = column[S]("id", O.PrimaryKey, O.AutoInc)

  def account = column[S]("account", O.SqlType("varchar(12)"))

  def password = column[S]("password")

  def accountIndex = index("account_index", account, unique = true)

  def * = (id.?, account, password) <> (UserAccount.tupled, UserAccount.unapply)
}
