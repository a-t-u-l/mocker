name := """mocker"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.11"

PlayKeys.devSettings := Seq("play.server.http.port" -> "9001")

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += javaJpa
libraryDependencies += "com.h2database" % "h2" % "1.4.194"
libraryDependencies += "org.apache.commons" % "commons-io" % "1.3.2"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"