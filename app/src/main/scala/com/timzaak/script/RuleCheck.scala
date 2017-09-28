package com.timzaak.script

import java.net.URLEncoder

import scala.concurrent.ExecutionContext.Implicits.global
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import scala.util.{Failure, Success}

object RuleCheck extends App {
  //easydata-prerelease-1.cp9cbbjb0tpe.rds.cn-north-1.amazonaws.com.cn
  implicit val backend =
    Database.forURL(url = s"jdbc:postgresql://easydata-prerelease-1.cp9cbbjb0tpe.rds.cn-north-1.amazonaws.com.cn:7531/growing?user=apps&password=${URLEncoder.encode("v,4)5Y|5G1PzF+fL","utf-8")}", driver = "org.postgresql.Driver")

//  backend.run(sql"select count(*) from rules where key like '%b==%'".as[Int]).foreach(count => println(s"$count 个 rule 带有 platform"))
//  backend.run(sql"""select id from rules where key in (select regexp_replace(key,'b==\:(iOS|web|Android)\:','') from rules where key like '%b==%')""".as[String]).flatMap{ ruleIdsWithNoB =>
//      println(s"${ruleIdsWithNoB.size} 个 rule 既有 b==:$$platform 又没有 b==:$$platform")
//      backend.run(sql"select id from rules where key like '%b==%'".as[String]).flatMap{ruleIds=>
//        backend.run(sql"select id,expression from original_metrics".as[(Int,String)]).map{seqs =>
//          val errorOriginalMetricsIds = seqs.collect {
//            case (id,expression) if expression.split(',').exists(ruleIds.contains)=>
//              id
//          }
//          println(s"${errorOriginalMetricsIds.size} 个Original指标使用 b==:$$platform rule")
//        }
//      }
//  }.onComplete{
//    case Failure(e) => e.printStackTrace()
//    case Success(()) => println("ok")
//  }

  val demo = for {
    badRuleIds <- backend.run(sql"""select b.id from rules, (select id,regexp_replace(key,'b==\:(iOS|web|Android)\:','') as key from rules where key like '%b==%') b where rules.key in (b.key)""".as[String])
    idWithExpressions <- backend.run(sql"""select id,expression from original_metrics""".as[(Int,String)])
  } yield {
    val errorMetrics = idWithExpressions.collect {
      case (id,express) if express.split(',').exists(badRuleIds.contains) =>
      id
    }
    println(s"${errorMetrics.size} 个Original指标使用 b==:$$platform rule，该 rule 同时拥有没有 b==:$$platform 的对应 rule")
  }
  demo.onComplete{
        case Failure(e) => e.printStackTrace()
        case Success(()) => println("ok")
      }

    Thread.sleep(1000*60*3)

//  1071 个 rule 带有 b==:$platform
//  589 个 rule 既有 b==:$platform 又没有 b==:$platform
//  963 个Original指标使用 b==:$platform rule
//  516 个Original指标使用 b==:$platform rule，该 rule 同时拥有没有 b==:$platform 的对应 rule
}
