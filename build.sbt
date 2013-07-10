name := "scales"

version := "3.0"

organization := "edu.depauw"

scalaVersion := "2.10.1"

libraryDependencies += "org.scala-lang" % "scala-actors" % "2.10.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

scalacOptions ++= Seq( "-deprecation", "-feature" )
