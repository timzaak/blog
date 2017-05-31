package com.timzaak.generated

import com.typesafe.config.ConfigFactory
import slick.sql.SqlProfile.ColumnOption
import very.util.db.postgrel.PostgresProfileWithJson4S

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object CustomizedCodeGenerator extends App {
  //def main(args: Array[String]): Unit = {
  // prepare database
//    for(script <- Seq("tables")) {
//      // FIXME don't forget to adjust it according to your environment
//      val cmd = s"psql -U test -h 172.17.0.1 -p 5432 -d test -f /media/workspace/repos/slick-pg/examples/codegen-customization/src/sql/$script"
//      val exec = Runtime.getRuntime().exec(cmd)
//      if (exec.waitFor() == 0) {
//        println(s"$script finished.")
//      }
//    }

  // write the generated results to file

//  }
  val conf = ConfigFactory.load().getConfig("postgrel")
  val db = PostgresProfileWithJson4S.api.Database
    .forURL(conf.getString("url"), driver = "org.postgresql.Driver")
  // filter out desired tables

  lazy val codegen = db
    .run {
      PostgresProfileWithJson4S.defaultTables
        .flatMap(PostgresProfileWithJson4S.createModelBuilder(_, false).buildModel)
    }
    .map { model =>
      new slick.codegen.SourceCodeGenerator(model) {
        override def Table = new Table(_) { table =>
          override def Column = new Column(_) { column =>
            // customize db type -> scala type mapping, pls adjust it according to your environment
            override def rawType: String = model.tpe match {
              case "java.sql.Date"      => "java.time.LocalDate"
              case "java.sql.Time"      => "java.time.LocalTime"
              case "java.sql.Timestamp" => "java.time.LocalDateTime"
              // currently, all types that's not built-in support were mapped to `String`
              case "String" =>
                model.options
                  .find(_.isInstanceOf[ColumnOption.SqlType])
                  .map(_.asInstanceOf[ColumnOption.SqlType].typeName)
                  .map({
                    case "hstore"   => "Map[String, String]"
                    case "geometry" => "com.vividsolutions.jts.geom.Geometry"
                    case "int8[]"   => "List[Long]"
                    case _          => "String"
                  })
                  .getOrElse("String")
              case _ => super.rawType
            }
          }
        }

        // ensure to use our customized postgres driver at `import profile.simple._`
        override def packageCode(profile: String,
                                 pkg: String,
                                 container: String,
                                 parentType: Option[String]): String = {
          s"""
package ${pkg}
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object ${container} extends {
  val profile = ${profile}
} with ${container}
/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait ${container}${parentType.map(t => s" extends $t").getOrElse("")} {
  val profile: $profile
  import profile.api._
  ${indent(code)}
}
      """.trim()
        }
      }
    }

  Await.ready(
    codegen.map(
      _.writeToFile(
        "very.util.db.postgrel.PostgresProfileWithJson4S",
        args(0),
        "com.timzaak.generated",
        "Tables.scala"
      )
    ),
    20.seconds
  )
}
