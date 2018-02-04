package very.util.db.postgre

import com.github.tminglei.slickpg._
import org.json4s.{JValue, JsonMethods}
import slick.jdbc.{JdbcType, PostgresProfile}

trait PostgresPlainProfileWithJson4S
    extends PostgresProfile
    with PgJson4sSupport
    with PgDate2Support
    with PgArraySupport
    with array.PgArrayJdbcTypes {

  def pgjson = "jsonb"

  type DOCType = org.json4s.native.Document

  override val jsonMethods =
    org.json4s.native.JsonMethods.asInstanceOf[JsonMethods[DOCType]]

  override val api = MyAPI

  object MyAPI
      extends super.API
      with Json4sJsonPlainImplicits
      with Date2DateTimePlainImplicits
      with SimpleArrayPlainImplicits {

    implicit val json4sJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JValue](
        pgjson,
        (s) =>
          utils.SimpleArrayUtils
            .fromString[JValue](jsonMethods.parse(_))(s)
            .orNull,
        (v) =>
          utils.SimpleArrayUtils
            .mkString[JValue](j => jsonMethods.compact(jsonMethods.render(j)))(v)
      ).to(_.toList)

  }

  
  
}

object PostgresPlainProfileWithJson4S extends PostgresPlainProfileWithJson4S
