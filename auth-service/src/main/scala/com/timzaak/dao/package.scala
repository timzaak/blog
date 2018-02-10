package com.timzaak

import scalikejdbc.DBSession
import ws.very.util.lang.Implicits2

package object dao extends Implicits2{
  abstract class Dao(implicit val session:DBSession) {

  }
}
