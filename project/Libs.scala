import sbt._
import sbt.Keys._


object Libs {

  val testDeps = Seq(Deps.scalaTest, Deps.scalaCheck, Deps.akkaTest, Deps.streamTest, Deps.httpTest)

  val libsDeps = libraryDependencies ++= Seq(
    Deps.logger,
    Deps.logback,
    Deps.codec,
    Deps.config,
    Deps.commonsio,
    Deps.scalaXml,
    Deps.akkaActor,
    Deps.akkaStream,
    Deps.akkaHttp,
    Deps.akkaLog,
    Deps.akkaXml,
    Deps.circeCore,
    Deps.circeGeneric,
    Deps.circeParser,
    Deps.slick,
    Deps.slickHikaricp,
    Deps.pgSlickCore,
    Deps.pgSlick,
    Deps.pgSlickJson,
    Deps.pgSlickJts,
    Deps.lettuce) ++ testDeps

  val supermm2WikiDeps = libraryDependencies ++= Seq(Deps.pg, Deps.poi) ++ testDeps
}
