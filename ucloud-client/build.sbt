name := "ucloud_client"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val scalaj_http = RootProject(file("../very-util-scalaj-http"))



lazy val ucloud_client = project.in(file(".")).dependsOn(scalaj_http)
