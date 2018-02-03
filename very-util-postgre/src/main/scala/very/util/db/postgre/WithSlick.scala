package very.util.db.postgre

import slick.basic.BasicBackend
import slick.dbio.{DBIOAction, NoStream}

import scala.concurrent.{ExecutionContext, Future}

abstract class WithSlick(implicit protected val db: BasicBackend#DatabaseDef, ec: ExecutionContext) {

  import scala.language.implicitConversions
  implicit def dbRunActionImplicit[R](action: DBIOAction[R, NoStream, Nothing]): Future[R] = {
    db.run(action)
  }
}
