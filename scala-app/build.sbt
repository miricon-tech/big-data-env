ThisBuild / scalaVersion := "2.12.18"
val spVer = "3.5.3"

lazy val root = (project in file("."))
  .settings(
    name := "scala-alg-playground",
    organization := "com.miri",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql"   % spVer % "provided",
      "org.apache.spark" %% "spark-mllib" % spVer % "provided"
    )
  )

import sbtassembly.MergeStrategy
assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case "module-info.class"          => MergeStrategy.discard
  case x                            => MergeStrategy.first
}