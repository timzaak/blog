package very.util.db.postgrel

import PostgresProfileWithJson4S.api._
import slick.jdbc.GetResult

import scala.concurrent.Future

trait BaseSqlDSL extends WithPostgrel {
  protected def tableName: String

  protected def paginate[Entity](page: Int = 1,
              pageSize: Int = 10,
              whereClause: Option[String] = None,
              orderBy: Option[String] = None
             )(implicit v:GetResult[Entity]): Future[(Vector[Entity],Long)] = {
    val where = whereClause.map(x => s" where $x").getOrElse("")
    sql"""
       |select * from #$tableName
       | $where
       | ${orderBy.map(x => s" order by $x").getOrElse("")}
       |limit $pageSize offset ${pageSize * (page - 1)}
       """.stripMargin.as[Entity] zip
    sql"select count(*) from #$tableName $where".as[Long].head

  }

}
