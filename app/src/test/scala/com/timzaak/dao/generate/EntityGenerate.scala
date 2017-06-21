package com.timzaak.dao.generate

import org.scalacheck._
import very.util.security.Permission

trait EntityGenerate extends IdGenerate {
  val permissionGen = for {
    long <- Gen.choose(0, Long.MaxValue)
  } yield Permission(long)

  val resourceGen = Gen.alphaNumStr

}
