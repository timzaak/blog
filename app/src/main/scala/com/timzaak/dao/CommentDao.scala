package com.timzaak.dao

import java.time.LocalDateTime

import com.timzaak.entity.Comment
import slick.jdbc.GetResult
import very.util.db.postgrel.{BaseSqlDSL, WithPostgrel}
import very.util.db.postgrel.PostgresProfileWithJson4S.api._

import scala.concurrent.{Future, Promise}

trait CommentDao extends WithPostgrel with BaseSqlDSL {
  protected val tableName = "comments"

  implicit val getCommentImplicit = GetResult{r =>
    //r.nextLocalDateTime()
    Comment(r.nextString().toLong, r.nextString().toLong, r.nextString().toLong, r.nextString(), r.nextLocalDateTime())
  }

  def createComment(fromId: L, toId: L, content: S): Future[L] = {
    sql"insert into #$tableName(content,from_id,to_id,time) values ($content,$fromId, $toId,${LocalDateTime.now()}) returning id".as[L].head
  }

  def pages(userId: L, page: I, pageSize: I) = {
    paginate[Comment](
      page, pageSize, whereClause = s"to_id=$userId", orderBy = "time desc")
  }
}
