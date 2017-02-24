package very.util.db.postgrel

import com.github.tminglei.slickpg._
import slick.basic.Capability
import slick.jdbc.JdbcCapabilities

trait PostgresDriver extends ExPostgresProfile
  with PgArraySupport
  with PgDate2Support
  with PgRangeSupport
  with PgHStoreSupport
  with PgSearchSupport
  with PgNetSupport
  with PgLTreeSupport {

  def pgjson = "jsonb"

  override protected def computeCapabilities:Set[Capability] =
    super.computeCapabilities + JdbcCapabilities.insertOrUpdate

  override val api = MyAPI

  object MyAPI extends API with ArrayImplicits
    with DateTimeImplicits
    with NetImplicits
    with LTreeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants {

  }

}


object PostgresDriver extends PostgresDriver