import play.PlayScala
import CommonDependencies._
import net.virtualvoid.sbt.graph.Plugin._
import ServiceDependencies._
import scalariform.formatter.preferences._

name := """toggle-service"""

version := "0.1"

envVars := Map("HBC_BANNER" -> "someBanner")

val defaultSettings: Seq[Setting[_]] = Seq(
  scalaVersion  := "2.11.6",
  scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature","-Ywarn-unused-import"),
  libraryDependencies ++= commonDependencies
) ++ graphSettings

lazy val root = (project in file("."))
  .settings(defaultSettings: _*)
  .settings(
    libraryDependencies ++= serviceDependencies
  )
  .enablePlugins(PlayScala)

resolvers ++= Seq(
  "Saks Artifactory - Libs Release Local" at "http://repo.saksdirect.com:8081/artifactory/libs-release-local",
  "Saks Artifactory - Ext Release Local" at "http://repo.saksdirect.com:8081/artifactory/ext-release-local",
  "Saks Artifactory - Libs Release" at "http://repo.saksdirect.com:8081/artifactory/libs-release",
  "Saks Artifactory - Libs Release - SaaS" at "https://hbc.jfrog.io/hbc/libs-release/"
)
externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = true)

credentials += Credentials("Artifactory Realm", "hbc.jfrog.io", sys.env.get("JFROG_USER").getOrElse(""), sys.env.get("JFROG_PASS").getOrElse("") )

lazy val buildAll = TaskKey[Unit]("build-all", "Compiles and runs all unit and integration tests.")
lazy val buildZip = TaskKey[Unit]("build-zip","Publishes a zip file with the new code.")
lazy val preCommit = TaskKey[Unit]("pre-commit", "Compiles, tests, zips code, refreshes docker container, and then runs integration tests.")

buildAll <<= Seq(clean, compile in Compile, compile in Test, test in Test).dependOn

buildZip <<= ((packageBin in Universal) map { out =>
  println("Copying Zip file to docker directory.")
  IO.move(out, file(out.getParent + "/../../docker/" + out.getName))
}).dependsOn(buildAll)

preCommit := {"./refresh-service.sh"!}

preCommit <<= preCommit.dependsOn(buildZip)

scalariformSettings

ScalariformKeys.preferences := FormattingPreferences()
  .setPreference( AlignParameters, true )
  .setPreference( AlignSingleLineCaseStatements, true )
  .setPreference( DoubleIndentClassDeclaration, true )
