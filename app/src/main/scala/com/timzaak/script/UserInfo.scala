package com.timzaak.script

import java.net.URLEncoder

import slick.jdbc.{PositionedParameters, SetParameter}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by timzaak on 2017/6/28.
  */

object UserInfo extends App {
  implicit val account =
    Database.forURL(url = s"jdbc:postgresql://127.0.0.1:5401/growing_accounts?user=apps&password=${URLEncoder.encode("v,4)5Y|5G1PzF+fL","utf-8")}", driver = "org.postgresql.Driver")

  implicit val backend =
    Database.forURL(url = s"jdbc:postgresql://127.0.0.1:5401/growing?user=apps&password=${URLEncoder.encode("v,4)5Y|5G1PzF+fL","utf-8")}", driver = "org.postgresql.Driver")

  val NEW = "new"
  //永不显示
  val REJECT = "reject"
  // 延迟显示
  val PENDING = "pending"
  // 通过growing web 点开问卷
  val CLICKED = "clicked"
  //完成
  val DONE = "done"
  val saleLevels = Seq("S(战略客户)","A(重点客户)","B(小型商机)","C(非优先客户)")

  saleLevels.foreach {saleLevel=>

    (account.run {
      sql"SELECT id FROM accounts WHERE profile ->'sale_level'=${saleLevel}".as[Int]
    }).map { userIds =>
      println("<><>",userIds.length)
      val statusSeq = Seq(NEW, REJECT, PENDING, CLICKED, DONE)
      statusSeq.foreach { status =>
        (backend.run {
          sql"SELECT count(*) FROM user_surveys WHERE user_id IN (#${userIds.mkString(",")}) AND status=${status}".as[Long].head
        }).map{ num=>
          println(s"客户等级：${saleLevel}, 浏览状态：${status}， 人数:${num}")
        }.recover{
          case e=> e.printStackTrace()
        }
      }
    }.recover{
      case e => e.printStackTrace()
    }

  }

  Thread.sleep(1000*60*10)
  println("over")

}
