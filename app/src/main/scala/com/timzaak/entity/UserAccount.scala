package com.timzaak.entity

case class UserAccount(id: O[L], accountName: S, password: S)

case class UserAuth(id: Option[L]/*,role:L, groupId*/)
