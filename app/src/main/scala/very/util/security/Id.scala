package very.util.security

import org.pico.hashids._
import org.pico.hashids.syntax._

sealed trait Id {
  type T

  def hashId: String

  def dbId: T
}

case class IntId(hashId: String, dbId: Int) extends Id {
  type T = Int
}

object IntId {
  def apply(hashId: String)(implicit hashids: Hashids):IntId = IntId(hashId, hashId.unhashid.head.toInt)

  def apply(dbId: Int)(implicit hashids: Hashids):IntId = IntId(dbId.hashid, dbId)
}

case class LongId(hashId: String, dbId: Long) extends Id {
  type T = Long
}

object LongId {
  def apply(hashId: String)(implicit hashids: Hashids):LongId = LongId(hashId, hashId.unhashid.head)

  def apply(dbId: Long)(implicit hashids: Hashids):LongId = LongId(dbId.hashid, dbId)
}
