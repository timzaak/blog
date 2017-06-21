import com.typesafe.config.ConfigFactory
name := "backend"
version := "1.0"

description := "An example GraphQL server written with akka-http and sangria."

scalaVersion := "2.12.1"
scalacOptions ++= Seq("-deprecation", "-feature")

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria"               % "1.0.0",
  "com.typesafe.akka"   %% "akka-http"             % "10.0.3",
  "com.pauldijou"       %% "jwt-core"              % "0.12.0",
  "com.aliyun.mns"      % "aliyun-sdk-mns"         % "1.1.8",
  "de.heikoseeberger"   %% "akka-http-json4s"      % "1.12.0",
  "org.sangria-graphql" %% "sangria-json4s-native" % "1.0.0",
  "com.pauldijou"       %% "jwt-json4s-native"     % "0.12.0",
  "org.picoworks"       %% "pico-hashids"          % "4.4.141",
  "org.scalatest"       %% "scalatest"             % "3.0.1" % Test,
  "org.scalacheck"      %% "scalacheck"            % "1.13.4" % Test,
  "com.typesafe.slick"  %% "slick-testkit"         % "3.2.0" % Test
)
lazy val slickSetting = Seq(
  scalaVersion := "2.12.1",
  libraryDependencies ++= List(
    //slick posggreql
    "org.postgresql"      % "postgresql" % "42.0.0",
    "com.typesafe.slick"  %% "slick"     % "3.2.0",
    "com.github.tminglei" %% "slick-pg"  % "0.15.0-M4",
    // json4s adapter
    "com.github.tminglei" %% "slick-pg_json4s" % "0.15.0-M4"
  )
)

def latestScalafmt = "1.0.0-RC1"
commands += Command.args("scalafmt", "Run scalafmt cli.") {
  case (state, args) =>
    val Right(scalafmt) =
      org.scalafmt.bootstrap.ScalafmtBootstrap.fromVersion(latestScalafmt)

    scalafmt.main("--non-interactive" +: args.toArray)
    state
}

lazy val lang = RootProject(file("../very-util-lang"))

lazy val json = RootProject(file("../very-util-json"))

lazy val redis = RootProject(file("../very-util-db-redis"))

lazy val log = RootProject(file("../very-util-log"))

lazy val scalaj = RootProject(file("../very-util-scalaj-http"))
lazy val codegen = project
  .settings(slickSetting)
  .settings(libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.2.0")
  .dependsOn(json)

lazy val root = (project in file("."))
  .dependsOn(json, lang, redis, codegen, log, scalaj)
  .settings(slickSetting)

flywayUrl := {
  val conf = ConfigFactory
    .parseFile((resourceDirectory in Compile).value / "application.conf")
    .resolve()
  conf.getString("postgrel.url")
}

flywayUrl in Test := {
  val conf = ConfigFactory
    .parseFile((resourceDirectory in Test).value / "application.conf")
    .resolve()
  conf.getString("postgrel.url")
}

parallelExecution in Test := false

testOptions in Test += Tests.Setup(() => {
  (flywayMigrate in Test).value
})

//package
enablePlugins(JavaAppPackaging)
mainClass in Compile := Some("Server")
/*  .settings(slick := slickCodeGenTask.value)  //no need without code generate

  //.settings(sourceGenerators in Compile += slickCodeGenTask.taskValue) // register automatic code generation on every compile, remove for only manual use)

// code generation task
lazy val slick = TaskKey[Seq[File]]("gen-tables")
lazy val slickCodeGenTask = Def.task {
  val dir = sourceManaged.value
  val cp = (dependencyClasspath in Compile).value
  val r = (runner in Compile).value
  val s = streams.value
  val outputDir = (dir / "slick").getPath

  toError(r.run("com.timzaak.generated.CustomizedCodeGenerator", cp.files, Array(outputDir), s.log))
  val fname = outputDir + "/com/timzaak/generated/Tables.scala"
  Seq(file(fname))
}
unmanagedSourceDirectories in Compile += sourceManaged.value / "slick"
 */
