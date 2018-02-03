import com.typesafe.config.ConfigFactory
name := "backend"
version := "1.0"

description := "Try everything I like"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:implicitConversions")

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria"               % "1.3.0",
  "com.pauldijou"       %% "jwt-core"              % "0.12.0",
  "com.aliyun.mns"      % "aliyun-sdk-mns"         % "1.1.8",
  "de.heikoseeberger"   %% "akka-http-json4s"      % "1.12.0",
  "org.sangria-graphql" %% "sangria-json4s-native" % "1.0.0",
  "com.pauldijou"       %% "jwt-json4s-native"     % "0.12.0",
  "org.picoworks"       %% "pico-hashids"          % "4.4.141",
  "com.github.cb372"    %% "scalacache-core"       % "0.10.0",
  "com.sksamuel.avro4s" %% "avro4s-core"           % "1.8.0",
  //"io.kamon"            %% "kamon-core"            % "1.0.0-RC5",
  "nl.grons"            %% "metrics-scala"         % "3.5.9",
  "org.scalatest"       %% "scalatest"             % "3.0.1" % Test,
  "org.scalacheck"      %% "scalacheck"            % "1.13.4" % Test,
  "com.typesafe.akka"   %% "akka-testkit"          % "2.5.6" % Test,
  "net.manub"           %% "scalatest-embedded-kafka" % "1.0.0" % "test",
  "net.manub"           %% "scalatest-embedded-kafka-streams" % "1.0.0" % "test",
  "com.typesafe.slick"  %% "slick-testkit"         % "3.2.0" % Test
)

def latestScalafmt = "1.3.0"

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

lazy val akka = RootProject(file("../very-util-akka"))

lazy val postgre = RootProject(file("../very-util-postgre"))

lazy val root = (project in file("."))
  .dependsOn(json, lang, redis, postgre, log, scalaj, akka)

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
  //(flywayMigrate in Test).value
})

//package
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
