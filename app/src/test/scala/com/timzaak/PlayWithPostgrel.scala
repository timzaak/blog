package com.timzaak

import slick.ast.ColumnOption
import slick.sql.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Await
import scala.util.{Failure, Success}
//import slick.lifted.{TableQuery, Tag}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._
import scala.concurrent.ExecutionContext.Implicits.global

case class User(id: Option[Int], first: S, last: S)

class Users(tag: Tag) extends Table[User](tag, "users") {

  def id = column[I]("id", O.PrimaryKey, O.AutoInc)

  def first = column[S]("first", O.SqlType("text"))

  def last = column[S]("last")

  def * = (id.?, first, last) <> (User.tupled, User.unapply)
}

//class UserTable(tag:Tag) extends Table[Test](){
//
//}
object PlayWithPostgrel extends App {
  val users = TableQuery[Users]

  val db = Database.forURL(url = "jdbc:postgresql://localhost:5432/postgres?user=timzaak&password=timzaak", driver = "org.postgresql.Driver")

  db.run(users.schema.create) onComplete {
    case Success(s) => println("sccess", s)
    case Failure(e) => e.printStackTrace()
  }

  Thread.sleep(1000 * 100)

}
