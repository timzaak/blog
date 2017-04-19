package com.timzaak.dao

import java.time.LocalDateTime

import com.timzaak.entity.Comment
import slick.jdbc.GetResult
import very.util.db.postgrel.{BaseSqlDSL, WithPostgrel}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import scala.concurrent.Future

trait CommentDao extends WithPostgrel with BaseSqlDSL {
  protected val tableName = "comments"

  implicit val getCommentImplicit = GetResult(r => Comment(r.<<,r.<<, r.<<, r.<<, r.<<))

  def createComment(userId: L, content: S): Future[L] = {
    sql"insert into #$tableName(content,user_id) values($content,$userId) returning id".as[L].head
  }

  def pages(userId: S, page: I, pageSize: I) = paginate[Comment](
    page, pageSize, whereClause= s"to_id=$userId" ,orderBy = "time desc")

}
