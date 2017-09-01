import sbt._

/* List the dependencies that are common across all microservices
 * DO NOT list dependencies that are specific to a microservice. Use 'ServiceDependencies' instead. */
object CommonDependencies {

  val scalaTestVersion = "2.2.5"
  val scalaCheckVersion = "1.12.2"
  val apiDocVersion = "20"
  val playWSVersion = "2.3.9"
  val playMockWSVersion = "2.3.0"
  val sprayVersion = "1.3.3"
  val logstashLogbackEncoderVersion = "4.6"

  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  val apiDoc = "com.hbc" %% "api_doc" % apiDocVersion
  val scalacheck = "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
  val playWS = "com.typesafe.play" %% "play-ws" % playWSVersion
  val sprayCaching = "io.spray" %% "spray-caching" % sprayVersion
  val playMockWS = "de.leanovate.play-mockws" %% "play-mockws" % playMockWSVersion % "test"
  val logstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % logstashLogbackEncoderVersion

  val commonDependencies : Seq[ModuleID] =
    Seq(
      playWS,
      playMockWS,
      scalaTest,
      apiDoc,
      scalacheck,
      sprayCaching,
      logstashLogbackEncoder
    )
}
