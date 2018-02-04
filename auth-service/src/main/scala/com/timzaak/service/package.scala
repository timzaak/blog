package com.timzaak

import ws.very.util.lang.Implicits2
import scala.concurrent.ExecutionContext

package object service extends Implicits2 {

  abstract class Service(implicit val ec: ExecutionContext)

}
