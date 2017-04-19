package very.util.graphql

import sangria.schema._

trait CommonSchema {
  val accArg = Argument("acc", StringType, description = "acc for login")


}

object CommonSchema extends CommonSchema