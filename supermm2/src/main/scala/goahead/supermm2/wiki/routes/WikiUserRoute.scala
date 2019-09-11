package goahead.supermm2
package wiki
package routes

import akka.pattern._
import goahead.libs.model._
import goahead.supermm2.wiki.models.{Admin, Course, Maker}
import goahead.supermm2.wiki.actors.WikiUserActor._

import scala.concurrent.Future
import goahead.supermm2.jmodel._

final case class WikiUserRoute(actors: Actors) extends Supermm2Route(actors) {
  import actors._

  implicit val AdminJson = Jsons.format[Admin]

  override def jsonHandler: JAction = {
    case HttpAction("Supermm2Wiki.GetTopTrendingCourses")       => getTopTrendingCourses
    case HttpAction("Supermm2Wiki.GetCourseById")               => getCourseById
    case HttpAction("Supermm2Wiki.AddCourse")                   => addCourse
    case HttpAction("Supermm2Wiki.UpdateCourse")                => updateCourse
    case HttpAction("Supermm2Wiki.GetTopMakersDesc")            => getTopMakersDesc
    case HttpAction("Supermm2Wiki.SignUp")                      => signUp
  }

  def signUp(req: Supermm2Message): Future[Admin] = {
    withForm(req.body) { form: SignUp =>
      WikiActor.ask(form).mapTo[Admin]
    }
  }

  def getTopTrendingCourses(req: Supermm2Message): Future[Seq[Course]] =
    WikiActor.ask(GetTopTrendingCourses).mapTo[Seq[Course]]

  def getCourseById(req: Supermm2Message): Future[Option[Course]] = {
    withForm(req.body) { form: QueryCourseForm =>
      WikiActor.ask(form).mapTo[Option[Course]]
    }
  }

  def addCourse(req: Supermm2Message): Future[Course] = {
    withForm(req.body) { form: AddCourseForm =>
      WikiActor.ask(form).mapTo[Course]
    }
  }

  def updateCourse(req: Supermm2Message): Future[Boolean] = {
    withForm(req.body) { form: UpdateCourseForm =>
      WikiActor.ask(form).mapTo[Boolean]
    }
  }

  def getTopMakersDesc(req: Supermm2Message): Future[Seq[Maker]] = {
    WikiActor.ask(GetTopMakers).mapTo[Seq[Maker]]
  }
}
