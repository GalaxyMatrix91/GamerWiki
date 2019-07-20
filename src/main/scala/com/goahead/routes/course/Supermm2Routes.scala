package com.goahead.routes.course

import akka.actor.ActorRef
import com.goahead.models.DataBase
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.util.Timeout
import com.goahead.models.course.{Course, Courses}
import de.heikoseeberger.akkahttpcirce.CirceSupport._
import akka.pattern._
import com.goahead.Errors
import com.goahead.actors.Supermm2Actor.{FindByCourseID, GetTrendingCourses, UpdateTrendingCourse}

import scala.concurrent.{ExecutionContext, Future}

class Supermm2Routes(db: DataBase, smm2Actor: ActorRef, timeout: Timeout, ec: ExecutionContext) extends Supermm2Route {
  implicit val database = db
  implicit val executionContext = ec
  implicit val requestTimeout = timeout
  implicit val actor = smm2Actor
}

trait Supermm2Route extends Supermm2api {
  import StatusCodes._
  import io.circe.generic.auto._
  def routes: Route =
    pathPrefix("wiki" / "supermm2" / "trendingtop10") {
      pathEndOrSingleSlash {
        get {
          onSuccess(getTrendingCourses()) { courses =>
            val trending_courses = new Courses(courses)
            complete(trending_courses)
          }
        } ~
          post {
            entity(as[Course]) { req =>
              onSuccess(addNewTrendingCourse(req)) { i =>
                  complete(i)
              }
            }
          }
      }
    } ~
    pathPrefix("wiki") {
      pathEndOrSingleSlash {
        get {
          complete(OK)
        }
      }
    } ~
    pathPrefix("wiki" / "supermm2" / "findcoursebyid") {
      (get & parameters("courseid")) {
        param1 =>
          onSuccess(findCourseById(FindByCourseID(param1))) {
            case Some(course)     => complete(course)
            case None             => complete(Errors(404, "Course Not Found"))
          }
      }
    }
}

trait Supermm2api {
    implicit val database: DataBase
    implicit val executionContext: ExecutionContext
    implicit val requestTimeout: Timeout
    val actor: ActorRef

    def getTrendingCourses():Future[Seq[Course]] = {
      actor.ask(GetTrendingCourses).mapTo[Seq[Course]]
    }

    def findCourseById(form: FindByCourseID): Future[Option[Course]] = {
      actor.ask(form).mapTo[Option[Course]]
    }
    def addNewTrendingCourse(req: Course):Future[Int] = {
      actor.ask(UpdateTrendingCourse(req)).mapTo[Int]
    }

}
