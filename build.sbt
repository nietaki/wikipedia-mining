name := "wikipedia_mining"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.2.1",
  "org.jsoup" % "jsoup" % "1.8.1",
  "org.specs2" %% "specs2-core" % "2.4.15" % "test",
  "com.typesafe.play" %% "play-json" % "2.3.7",
  "org.scalesxml" % "scales-xml_2.11" % "0.6.0-M3"
  )
