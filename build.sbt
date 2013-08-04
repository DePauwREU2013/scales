name := "scales"

version := "3.0"

organization := "edu.depauw"

scalaVersion := "2.10.1"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-actors" % "2.10.1",
  "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test",
  "cc.co.scala-reactive" % "reactive-core_2.10" % "0.3.2.1" withSources() withJavadoc()
)

scalacOptions ++= Seq( "-deprecation", "-feature" )

// Remove javaSource from build directories, so sbteclipse won't create them
unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(Seq(_))

unmanagedSourceDirectories in Test <<= (scalaSource in Test)(Seq(_))