name := "seed"
version := "1.0"

description := "app cluster"

scalaVersion := "2.12.4"

lazy val akka = RootProject(file("../very-util-akka"))

lazy val seed =  (project in file(".")).dependsOn(akka)

//mainClass in Compile := Some("Server")

