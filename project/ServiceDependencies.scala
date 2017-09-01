import sbt._

/* List the dependencies specific to the service here */
object ServiceDependencies {

  val casbahVersion = "2.8.1"
  val casbah = "org.mongodb" %% "casbah" % casbahVersion
  val mongoDriverVersion = "2.13.1"
  val mongoDB = "org.mongodb" % "mongo-java-driver" % mongoDriverVersion
  val salatVersion = "1.9.9"
  val salat = "com.novus" %% "salat" % salatVersion
  val authVersion = "0.13.5"

  val auth = "jp.t2v" %% "play2-auth"% authVersion
  val authTest = "jp.t2v" %% "play2-auth-test" % authVersion % "test"

  val slf4jApiVersion = "1.7.12"
  val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jApiVersion

  val awsSSMJavaSDK = "com.amazonaws" % "aws-java-sdk-ssm" % "1.11.155"

  val serviceDependencies : Seq[ModuleID] = Seq(
    auth,
    authTest,
    casbah,
    mongoDB,
    salat,
    slf4jApi,
    awsSSMJavaSDK
  )
}
