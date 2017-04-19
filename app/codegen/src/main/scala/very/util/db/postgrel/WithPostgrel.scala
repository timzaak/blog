package very.util.db.postgrel

import slick.basic.BasicBackend
import slick.dbio.{DBIOAction, NoStream}

import scala.concurrent.Future


abstract class WithPostgrel(implicit val db: BasicBackend#DatabaseDef){

  import scala.language.implicitConversions
  implicit def dbRunActionImplicit[R](action: DBIOAction[R, NoStream, Nothing]):Future[R]={db.run(action)}
}
