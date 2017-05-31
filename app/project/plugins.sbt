addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.0-M8")
addSbtPlugin("org.flywaydb"     % "flyway-sbt"          % "4.1.2")

libraryDependencies += "com.geirsson" %% "scalafmt-bootstrap" % "0.6.6"

resolvers += "Flyway" at "https://flywaydb.org/repo"
