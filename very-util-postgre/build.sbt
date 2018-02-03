name := "very-util-postgre"

description := "wrapper of postgre and slick"

scalaVersion := "2.12.4"

libraryDependencies ++= List(
  //slick postgresql
  "org.postgresql" % "postgresql" % "42.0.0",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "com.github.tminglei" %% "slick-pg" % "0.15.0-M4",
  // json4s adapter
  "com.github.tminglei" %% "slick-pg_json4s" % "0.15.0-M4"
)

lazy val json = RootProject(file("../very-util-json"))

lazy val postgre = project.in(file(".")).dependsOn(json)
