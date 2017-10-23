package com.timzaak.chaos

import com.timzaak.TestSpec
import ws.very.util.json.JsonHelperWithDoubleMode

class CaseClassStatusSPec extends TestSpec with JsonHelperWithDoubleMode {

  object StatusEnum extends Enumeration {
    type StatusEnum = Value
    val Done   = Value("done")
    val Doing  = Value("doing")
    val Failed = Value("failed")
  }

  class ParentStatus(status: StatusEnum.StatusEnum,
                     _error: Option[S] = None,
                     _filePath: O[S] = None,
                     _fileName: Option[S] = None)

  case class Success(filePath: S, fileName: S)
      extends ParentStatus(status = StatusEnum.Done,
                           _error = None,
                           _fileName = Some(fileName),
                           _filePath = Some(filePath))

  case class Failure(error: S)
      extends ParentStatus(status = StatusEnum.Failed,
                           _error = Some(error),
                           _fileName = None,
                           _filePath = None)

  "class with case children" - {
    "can not  serialize to the same json string" in {
      import org.json4s.native.Serialization.write

      write(
        new ParentStatus(status = StatusEnum.Done,
                         _fileName = Some("fileName"),
                         _filePath = Some("fileName"))
      ) shouldNot be(write(Success("filePath", "fileName"): ParentStatus))
    }

  }
}
