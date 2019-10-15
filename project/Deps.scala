import sbt._

object Deps {
  // val ScalaV = "2.12.8"
  val ScalaV = "2.13.0"
  val AkkaV = "2.6.0-M5"
  val AkkaHttpV = "10.1.9"
  val CirceV = "0.12.0-RC3"
  val SlickV = "3.3.2"
  val PgSlickV = "0.18.0"

  // logger & common
  val logger = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val config = "com.typesafe" % "config" % "1.3.4"
  val commonsio = "commons-io" % "commons-io" % "2.6"
  val codec = "commons-codec" % "commons-codec" % "1.13"

  // scala & akka & kafka
  val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % AkkaV
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % AkkaV
  val akkaLog = "com.typesafe.akka" %% "akka-slf4j" % AkkaV
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % AkkaHttpV
  val akkaXml = "com.typesafe.akka" %% "akka-http-xml" % AkkaHttpV
  val akkaKafka = "com.typesafe.akka" %% "akka-stream-kafka" % "1.0.5"
  val kafka = "org.apache.kafka" % "kafka-clients" % "2.3.0"

  // circe
  val circeCore = "io.circe" %% "circe-core" % CirceV
  val circeGeneric = "io.circe" %% "circe-generic" % CirceV
  val circeParser = "io.circe" %% "circe-parser" % CirceV

  // slick
  val slick = "com.typesafe.slick" %% "slick" % SlickV
  val slickHikaricp = "com.typesafe.slick" %% "slick-hikaricp" % SlickV
  val pgSlickCore = "com.github.tminglei" %% "slick-pg_core" % PgSlickV
  val pgSlick = "com.github.tminglei" %% "slick-pg" % PgSlickV
  val pgSlickJson = "com.github.tminglei" %% "slick-pg_circe-json" % PgSlickV
  val pgSlickJts = "com.github.tminglei" %% "slick-pg_jts" % PgSlickV
  val pg = "org.postgresql" % "postgresql" % "42.2.8"
  val h2 = "com.h2database" % "h2" % "1.4.199"
  val mysql = "mysql" % "mysql-connector-java" % "5.1.47"

  // jedis
  val jedis = "redis.clients" % "jedis" % "3.1.0"
  val lettuce = "io.lettuce" % "lettuce-core" % "5.1.8.RELEASE"

  // zookeeper
  val zookeeper = "org.apache.zookeeper" % "zookeeper" % "3.4.14"

  //Apache POI
  val poi = "org.apache.poi" % "poi-ooxml" % "4.1.0"

  // Test
  val scalaTest = "org.scalatest" %% "scalatest" % "3.1.0-SNAP13" % "test"
  val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"
  val akkaTest = "com.typesafe.akka" %% "akka-testkit" % AkkaV % Test
  val streamTest = "com.typesafe.akka" %% "akka-stream-testkit" % AkkaV % Test
  val httpTest = "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpV % Test
  // akka cluster
  val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % AkkaV
  val akkaClusterTools ="com.typesafe.akka" %% "akka-cluster-tools" % AkkaV

}
