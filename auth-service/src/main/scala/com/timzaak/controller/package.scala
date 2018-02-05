package com.timzaak

import ws.very.util.lang.Implicits2
import play.api.mvc._
import scala.concurrent.ExecutionContext

package object controller extends Implicits2{

  abstract class Controller(implicit val ec:ExecutionContext, val controllerComponents: ControllerComponents) extends BaseController
}
