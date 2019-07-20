package com.goahead.actors

import akka.actor.{Actor, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.Materializer
import akka.util.Timeout
import com.goahead.models.DataBase
import com.goahead.routes.course.Supermm2Routes

import com.goahead.util.db.{DatabaseConnector, DatabaseMigrationManager}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext}
import com.typesafe.config.{ Config, ConfigFactory }

final class RoutesActor private (m: Materializer)
  extends Actor with RequestTimeout {

  implicit val actorSystem: ActorSystem = context.system
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val materialiser: Materializer = m

  //MySQL config
  val mysql_config = ConfigFactory.load()
  new DatabaseMigrationManager(
    mysql_config.getString("database.mysql-jdbc-url"),
    mysql_config.getString("database.mysql-user"),
    mysql_config.getString("database.mysql-password")
  ).migrateDatabaseSchema()
  val mysql_databaseConnector = new DatabaseConnector(
    mysql_config.getString("database.mysql-jdbc-url"),
    mysql_config.getString("database.mysql-user"),
    mysql_config.getString("database.mysql-password"))

  //PostgreSQL config
  /*  val pg_config = ConfigFactory.load()
    new DatabaseMigrationManager(
      pg_config.getString("database.pg-jdbc-url"),
      pg_config.getString("database.pg-user"),
      pg_config.getString("database.pg-password")
    ).migrateDatabaseSchema()
    val pg_databaseConnector = new DatabaseConnector(
      pg_config.getString("database.pg-jdbc-url"),
      pg_config.getString("database.pg-user"),
      pg_config.getString("database.pg-password"))
  */
  val mysql_db = new DataBase(mysql_databaseConnector, executor)
  lazy val smm2_actor = context.actorOf(Props(new Supermm2Actor(mysql_db, executor)), "Smm2_Actor")
  val api = new Supermm2Routes(mysql_db, smm2_actor, requestTimeout(mysql_config), executor)
  val bindingFuture = Http().bindAndHandle(api.routes, mysql_config.getString("http.interface"), mysql_config.getInt("http.port"))

  override def receive: Receive = { case _ => }

  override def postStop(): Unit = {
    Await.result(bindingFuture.flatMap(_.unbind()), Duration.Inf)
  }
}

object RoutesActor {

  def props(materialiser: Materializer): Props =
    Props(new RoutesActor(materialiser))
}

trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}