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
  }

  def getTopTrendingCourses(req: Supermm2Message): Future[Seq[Course]] =
    WikiUserActor.ask(GetTopTrendingCourses).mapTo[Seq[Course]]
}
