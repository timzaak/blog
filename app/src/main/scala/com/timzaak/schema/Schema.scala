package com.timzaak.schema

import java.security.InvalidParameterException

import org.pico.hashids.Hashids
import sangria.schema.ScalarType
import sangria.validation.ValueCoercionViolation
import very.util.graphql.CommonSchema
import very.util.security.{ IntId, LongId }

trait Schema extends CommonSchema {
  implicit def hashids: Hashids

  case object IdValidation extends ValueCoercionViolation("id validation error")
  implicit val intIdType = ScalarType[IntId](
    name = "Date",
    description = Some("A string representing a date, in format [yyyy-MM-dd]."),
    coerceOutput = (d, caps) => d.hashId,
    coerceUserInput = {
      case s: String => Right(IntId(s))
      case _         => Left(IdValidation)
    },
    coerceInput = {
      case sangria.ast.StringValue(s, _, _) => Right(IntId(s))
      case _                                => Left(IdValidation)
    }
  )

  implicit val longIdType = ScalarType[LongId](
    name = "Date",
    description = Some("A string representing a date, in format [yyyy-MM-dd]."),
    coerceOutput = (d, caps) => d.hashId,
    coerceUserInput = {
      case s: String => Right(LongId(s))
      case _         => Left(IdValidation)
    },
    coerceInput = {
      case sangria.ast.StringValue(s, _, _) => Right(LongId(s))
      case _                                => Left(IdValidation)
    }
  )
}
