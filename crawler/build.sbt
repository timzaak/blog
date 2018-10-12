import Dependencies._

lazy val lang = RootProject(file("../very-util-lang"))

lazy val config = RootProject(file("../very-util-config"))

lazy val http = RootProject(file("../very-util-scalaj-http"))

libraryDependencies ++= Seq(
  scalaTest,
  selenium,
  jsoup,
  diffJ,
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.timzaak",
      scalaVersion := "2.12.7",
      version      := "0.1.0"
    )),
    name := "Crawler"
  ).dependsOn(lang, config, http)
