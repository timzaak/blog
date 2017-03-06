package com.timzaak.database

import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import com.timzaak.dao.UserAccountTable
import com.timzaak.di.DaoDI

import scala.util.{Failure, Success}

object InitDataBase extends App with DaoDI {

  //create table
  List(
    TableQuery[UserAccountTable]
  ).foreach { query =>
    db.run(query.schema.create) onComplete {
      case Success(_) => println(s"table ${query.baseTableRow.tableName} create success")
      case Failure(e) => println(e)
    }
  }

  //insert data

}
