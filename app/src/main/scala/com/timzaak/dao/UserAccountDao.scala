package com.timzaak.dao

import com.joyrec.util.db.redis.WithRedis
import com.timzaak.entity.UserAccount
import very.util.db.postgrel.WithPostgrel
import very.util.db.postgrel.PostgresProfileWithJson4S.plainAPI._

import scala.concurrent.Future

trait UserAccountDao extends WithPostgrel with WithRedis{


  protected val tableName = "user_accounts"


  import slick.jdbc.GetResult
  implicit val getUserAccountResult = GetResult(r => UserAccount(r.<<, r.<<, r.<<))

  def getByAccAndPwd(account: S, pwd: S): Future[Option[UserAccount]] = {
    sql"select * from #$tableName where acc=$account and pwd=$pwd".as[UserAccount].headOption
  }


  def newAcc(acc: UserAccount): Future[L] =
    sql"insert into #${tableName}(acc,pwd) values (${acc.accountName},${acc.password}) returning id".as[L].head

}
