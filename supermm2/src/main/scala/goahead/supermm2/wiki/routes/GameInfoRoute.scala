package goahead.supermm2
package wiki
package routes

import akka.http.scaladsl.server.Route
import akka.pattern._
import goahead.supermm2.wiki.models._
import goahead.supermm2.jmodel._

final case class GameInfoRoute(webRoot: String, actors: Actors) extends Supermm2Route(actors) {
  import actors._

  override def otherRoute: Route = {
    (post & path(webRoot / "GameWikis" / "addOrUpdateWiki")) {
      entity(as[AddGameInfoForm]) { form =>
        WikiActor.ask(form).mapTo[GameInfo]
      }
    } ~
      (get & path(webRoot / "GameWikis" / LongNumber)) { i =>
        val a = WikiActor.ask(QueryGameInfoForm(i)).mapTo[Option[GameInfo]]
        a.map {
          case Some(b) => b
          case _ => throw Errors.NotFound.extra(s"第${i}个Wiki不存在")
        }
      } ~
      (get & path(webRoot / "GameWikis" / "allGameWikiList")) {
        WikiActor.ask(QueryAllGameInfo).mapTo[GameInfos]
      }
  }
}
