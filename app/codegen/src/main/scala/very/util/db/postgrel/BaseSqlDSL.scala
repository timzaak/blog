package very.util.db.postgrel

import PostgresProfileWithJson4S.api._
import scala.reflect.runtime.universe._

import slick.jdbc.GetResult

import scala.concurrent.Future

trait BaseSqlDSL extends WithPostgrel {
  protected def tableName: String
  protected val fieldList: List[String] = List.empty
  protected val fields                  = fieldList.mkString(",")
//
  //danger.
  protected def paginate[Entity](
      page: Int = 1,
      pageSize: Int = 10,
      whereClause: Option[String] = None,
      orderBy: Option[String] = None
  )(implicit v: GetResult[Entity]): Future[(Vector[Entity], Long)] = {

    val where = whereClause.map(x => s" where $x").getOrElse("")
    db.run(
      sql"""#$select #$where #${orderBy
        .map(x => s" order by $x")
        .getOrElse("")} limit $pageSize offset ${pageSize * (page - 1)}"""
        .as[Entity]
    ) zip
      db.run(sql"select count(*) from #$tableName #$where".as[Long].head)
  }

  protected def select = "select #${fields} from #$tableName"

  def extractFieldNames[T: TypeTag] =
    typeOf[T].members
      .collect {
        case m: MethodSymbol if m.isCaseAccessor => m
      }
      .toList
      .map(_.name.toString)

  private def snakify(name: String) =
    name
      .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
      .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
      .toLowerCase

  def extractSnakeFields[T: TypeTag] = extractFieldNames[T].map(snakify)

}
