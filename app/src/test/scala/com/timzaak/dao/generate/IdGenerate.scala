package com.timzaak.dao.generate

import org.pico.hashids.Hashids
import org.scalacheck._
import very.util.security.{ IntId, LongId }

trait IdGenerate {
  implicit def hashids: Hashids

  val GroupIdGen: Gen[GroupId] =
    for {
      number <- Gen.chooseNum(1, Integer.MAX_VALUE, 100)
    } yield {
      IntId(number)
    }
  val UserIdGen: Gen[UserId] =
    Gen.chooseNum(1, Long.MaxValue, 100)

}
