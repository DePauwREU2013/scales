name := "scales"

version := "3.0"

organization := "edu.depauw"

scalaVersion := "2.10.1"

libraryDependencies += "org.scala-lang" % "scala-actors" % "2.10.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1" % "test"

libraryDependencies += "cc.co.scala-reactive" % "reactive-core_2.10" % "0.3.2.1"

scalacOptions ++= Seq( "-deprecation", "-feature" )
