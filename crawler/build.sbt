import Dependencies._

lazy val lang = RootProject(file("../very-util-lang"))

lazy val config = RootProject(file("../very-util-config"))

libraryDependencies ++= Seq(
  scalaTest,
  selenium
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.timzaak",
      scalaVersion := "2.12.5",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Crawler"
  ).dependsOn(lang, config)
