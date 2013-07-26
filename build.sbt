name := "scales"

version := "3.0"

organization := "edu.depauw"

scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-actors" % "2.10.1",
  "org.scalatest" %% "scalatest" % "1.9.1" % "test",
  "cc.co.scala-reactive" % "reactive-core_2.10" % "0.3.2.1" withSources() withJavadoc()
)

scalacOptions ++= Seq( "-deprecation", "-feature" )
