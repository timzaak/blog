package com.timzaak.dao

import org.pico.hashids.Hashids
import slick.basic.BasicBackend
import slick.jdbc.{GetResult, PositionedParameters, PositionedResult, SetParameter}
import very.util.db.postgrel.BaseSqlDSL
import very.util.security.{IntId, LongId}

abstract class Dao(implicit val hashids: Hashids,
                   implicit override val db: BasicBackend#DatabaseDef
) extends BaseSqlDSL {

  implicit object GetIntId extends GetResult[IntId] {
    def apply(rs: PositionedResult) = IntId(rs.nextInt())
  }

  implicit object GetOptionIntId extends GetResult[O[IntId]] {
    def apply(rs: PositionedResult) = rs.nextIntOption().map(IntId(_))
  }

  implicit object SetIntId extends SetParameter[IntId] {
    def apply(v: IntId, pp: PositionedParameters) {
      pp.setInt(v.dbId)
    }
  }

  implicit object GetLongId extends GetResult[LongId] {
    def apply(rs: PositionedResult) = LongId(rs.nextLong())
  }


  implicit object GetOptionLongId extends GetResult[O[LongId]] {
    def apply(rs: PositionedResult) = rs.nextLongOption().map(LongId(_))
  }

  implicit object SetLongId extends SetParameter[LongId] {
    def apply(v: LongId, pp: PositionedParameters) {
      pp.setLong(v.dbId)
    }
  }


}
