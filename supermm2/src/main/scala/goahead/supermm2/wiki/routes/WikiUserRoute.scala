package goahead.supermm2
package wiki
package routes

import akka.pattern._
import goahead.libs.model._
import goahead.supermm2.wiki.models.Course
import goahead.supermm2.wiki.actors.WikiUserActor._
import scala.concurrent.Future
import goahead.supermm2.jmodel._

final case class WikiUserRoute(actors: Actors) extends Supermm2Route(actors) {
  import actors._

  override def jsonHandler: JAction = {
    case HttpAction("Supermm2Wiki.GetTopTrendingCourses")       => getTopTrendingCourses
    case HttpAction("Supermm2Wiki.GetCourseById")               => getCourseById
    case HttpAction("Supermm2Wiki.AddCourse")                   => addCourse
    case HttpAction("Supermm2Wiki.UpdateCourse")                => updateCourse
  }

  def getTopTrendingCourses(req: Supermm2Message): Future[Seq[Course]] =
    WikiUserActor.ask(GetTopTrendingCourses).mapTo[Seq[Course]]

  def getCourseById(req: Supermm2Message): Future[Option[Course]] = {
    withForm(req.body) { form: QueryCourseForm =>
      WikiUserActor.ask(form).mapTo[Option[Course]]
    }
  }

  def addCourse(req: Supermm2Message): Future[Course] = {
    withForm(req.body) { form: AddCourseForm =>
      WikiUserActor.ask(form).mapTo[Course]
    }
  }

  def updateCourse(req: Supermm2Message): Future[Boolean] = {
    withForm(req.body) { form: UpdateCourseForm =>
      WikiUserActor.ask(form).mapTo[Boolean]
    }
  }

}
