package com.timzaak.database

import very.util.db.postgrel.PostgresProfileWithJson4S.api._
import com.timzaak.dao.UserAccountTable
import com.timzaak.di.DaoDI
import com.timzaak.entity.UserAccount
import scala.concurrent.duration._

import scala.concurrent.Await
import scala.util.{Failure, Success}

object InitDataBase extends App with DaoDI {

  //create table
  val createTablesStatements = List(
    TableQuery[UserAccountTable]
  ).map { query =>
    query.schema.create
  }
  val createTableActions = db.run(DBIO.seq(createTablesStatements: _*))
  createTableActions onComplete {
    case Success(_) => println("finish create tables")
    case Failure(e) => e.printStackTrace()
  }


  Await.result(createTableActions, Duration.Inf)

  //insert data
  val userAccounts = TableQuery[UserAccountTable]


  val insertActions = db.run(DBIO.seq(
    userAccounts += UserAccount(None, "admin", "pwd")
  ))

  insertActions onComplete {
    case Success(_) => println("finish insert info")
    case Failure(e) => e.printStackTrace()
  }

  Await.result(insertActions, Duration.Inf)
}
