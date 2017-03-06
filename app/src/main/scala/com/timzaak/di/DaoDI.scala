package com.timzaak.di

import very.util.db.postgrel.PostgresProfileWithJson4S.api._

trait DaoDI extends AkkaDI{

  private def password = "timzaak"
  private def user ="timzaak"

  val db = Database.forURL(
    url = s"jdbc:postgresql://localhost:5432/postgres?user=${user}&password=${password}",
    driver = "org.postgresql.Driver")



}
