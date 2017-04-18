package com.timzaak.schema

import sangria.schema._

trait CommonSchema {
  val accArg = Argument("acc", StringType, description = "acc for login")


}

object CommonSchema extends CommonSchema