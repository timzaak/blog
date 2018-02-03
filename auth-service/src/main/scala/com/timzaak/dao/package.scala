package com.timzaak

import very.util.db.postgre.WithSlick
import ws.very.util.lang.Implicits2

package object dao extends Implicits2{
  trait Dao extends WithSlick
}
