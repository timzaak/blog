package com.timzaak.dao

import com.timzaak.entity.Comment
import slick.jdbc.GetResult
import very.util.db.postgrel.PostgresProfileWithJson4S.api._
import scala.concurrent.Future

trait CommentDao extends Dao {

  override protected val fieldList: List[String] = extractSnakeFields[Comment]

  implicit val getCommentImplicit = GetResult { r =>
    Comment(r.<<, r.nextLong(), r.nextLong(), r.nextString(), r.nextLocalDateTime())
  }
  def createComment(fromId: L, toId: L, content: S): Future[L] = {
    sql"insert into #$tableName(content,from_id,to_id) values ($content,$fromId, $toId) returning id"
      .as[L]
      .head
  }

  def pages(userId: L, page: I, pageSize: I) = {
    paginate[Comment](page,
                      pageSize,
                      whereClause = s"to_id=$userId or from_id=${userId}",
                      orderBy = "created_at desc")
  }

  def all(): Future[Vector[Comment]] = {
    sql"select * from #$tableName".as[Comment]
  }
}
