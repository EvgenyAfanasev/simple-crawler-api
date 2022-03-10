scalaVersion := "2.13.8"

name         := "todo-api"
organization := "ru.afanasev.todo"
version      := "0.0.1"

val http4sVersion            = "0.23.10"
val jsoupVersion             = "1.14.3"
val loggingVersion           = "3.9.4"
val logbackVersion           = "1.2.11"
val newTypeVersion           = "0.4.4"
val circeVersion             = "0.14.1"
val scalaTestVersion         = "3.2.11"
val catsEffectTestingVersion = "1.4.0"

libraryDependencies += "org.http4s"                 %% "http4s-dsl"                    % http4sVersion
libraryDependencies += "org.jsoup"                  %  "jsoup"                         % jsoupVersion
libraryDependencies += "ch.qos.logback"             %  "logback-classic"               % logbackVersion
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging"                 % loggingVersion
libraryDependencies += "org.http4s"                 %% "http4s-dsl"                    % http4sVersion
libraryDependencies += "org.http4s"                 %% "http4s-blaze-server"           % http4sVersion
libraryDependencies += "org.http4s"                 %% "http4s-circe"                  % http4sVersion
libraryDependencies += "io.circe"                   %% "circe-generic"                 % circeVersion
libraryDependencies += "io.circe"                   %% "circe-literal"                 % circeVersion
libraryDependencies += "org.scalatest"              %% "scalatest"                     % scalaTestVersion         % Test
libraryDependencies += "org.typelevel"              %% "cats-effect-testing-scalatest" % catsEffectTestingVersion % Test

scalacOptions ++= Seq(
  "-Ymacro-annotations",
  "-encoding", "utf8",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

