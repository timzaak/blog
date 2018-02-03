name := "auth-service"

organization := "com.timzaak"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.3"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

lazy val lang = RootProject(file("../very-util-lang"))

lazy val json = RootProject(file("../very-util-json"))

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .disablePlugins(PlayLayoutPlugin)
  .disablePlugins(PlayLogback)
  .dependsOn(lang,json)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.timzaak.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.timzaak.binders._"
