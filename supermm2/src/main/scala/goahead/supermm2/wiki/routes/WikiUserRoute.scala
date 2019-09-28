package goahead.supermm2
package wiki
package routes

import akka.pattern._
import goahead.libs.model._
import goahead.supermm2.wiki.models._
import goahead.supermm2.wiki.actors.WikiUserActor._
import akka.http.scaladsl.server.Route
import scala.concurrent.Future
import goahead.supermm2.jmodel._

final case class WikiUserRoute(webRoot: String, actors: Actors) extends Supermm2Route(actors) {
  import actors._

  override def jsonHandler: JAction = {
    case HttpAction("Supermm2Wiki.GetTopTrendingCourses")       => getTopTrendingCourses
    case HttpAction("Supermm2Wiki.GetCourseById")               => getCourseById
    case HttpAction("Supermm2Wiki.GetTopMakersDesc")            => getTopMakersDesc
    case HttpAction("Supermm2Wiki.GetMakerById")                => getMakerById
    //case HttpAction("Supermm2Wiki.SignUp")                      => signUp
  }

  override def otherRoute: Route = {
    (get & path(webRoot / "Supermm2Wiki.GetTopTrendingCourses")) {
      WikiActor.ask(GetTopTrendingCourses).mapTo[Courses]
    } ~
      (get & path(webRoot / "Supermm2Wiki.GetTopMakersDesc")) {
        WikiActor.ask(GetTopMakers).mapTo[Makers]
      } ~
      (get & path(webRoot / "Supermm2Wiki.GetAllPoisonCourses")) {
        WikiActor.ask(GetAllPoisonCourses).mapTo[Courses]
      } ~
      (get & path(webRoot / "Supermm2Wiki.GetAllMakersByVersusRatingScore")) {
        WikiActor.ask(GetAllMakersByVersusRatingScore).mapTo[Makers]
      }
  }
  /*
  def signUp(req: Supermm2Message): Future[Admin] = {
    withForm(req.body) { form: SignUp =>
      WikiActor.ask(form).mapTo[Admin]
    }
  }
   */

  def getTopTrendingCourses(req: Supermm2Message): Future[Seq[Course]] =
    WikiActor.ask(GetTopTrendingCourses).mapTo[Seq[Course]]

  def getCourseById(req: Supermm2Message): Future[Option[Course]] = {
    withForm(req.body) { form: QueryCourseForm =>
      WikiActor.ask(form).mapTo[Option[Course]]
    }
  }

  def getTopMakersDesc(req: Supermm2Message): Future[Seq[Maker]] = {
    WikiActor.ask(GetTopMakers).mapTo[Seq[Maker]]
  }

  def getMakerById(req: Supermm2Message): Future[Option[Maker]] = {
    withForm(req.body) { form: QueryMakerForm =>
      WikiActor.ask(form).mapTo[Option[Maker]]
    }
  }

}
