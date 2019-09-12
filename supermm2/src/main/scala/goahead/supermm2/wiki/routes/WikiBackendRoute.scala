package goahead.supermm2
package wiki
package routes

import akka.http.scaladsl.server.Route
import goahead.supermm2._
import goahead.supermm2.wiki.models.{Course, Maker}
import akka.pattern._
import goahead.supermm2.wiki.actors.WikiUserActor._
import goahead.supermm2.jmodel._

final case class WikiBackendRoute(webRoot: String, actors: Actors) extends Supermm2Route(actors) {
  import actors._

  override def otherRoute: Route = upload

  def upload: Route = (post & path(webRoot / "upload" / "course")) {
    entity(as[UploadCourseForm]) { form =>
      auth { _ =>
        WikiActor.ask(form).mapTo[Course]
      }
    }
  }  ~ (post & path(webRoot / "upload" / "maker")) {
    entity(as[UploadMakerForm]) { form =>
      auth { _ =>
        WikiActor.ask(form).mapTo[Maker]
      }
    }
  }
}
