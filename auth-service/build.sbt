name := "auth-service"

organization := "com.timzaak"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.4"


val scalikejdbc = Seq(
  "org.skinny-framework" %% "skinny-orm" % "2.5.2",
  "org.postgresql" % "postgresql" % "42.2.4"
)

libraryDependencies ++= (Seq(
  "io.kanaka" %% "play-monadic-actions" % "2.1.0",
  "org.typelevel"       %% "cats-core"             % "1.2.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
) ++ scalikejdbc)

lazy val lang = RootProject(file("../very-util-lang"))

lazy val json = RootProject(file("../very-util-json"))

lazy val log = RootProject(file("../very-util-log"))

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .disablePlugins(PlayLogback)
  .dependsOn(lang, json, log)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.timzaak.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.timzaak.binders._"
