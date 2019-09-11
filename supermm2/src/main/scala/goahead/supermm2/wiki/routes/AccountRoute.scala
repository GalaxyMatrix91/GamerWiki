package goahead.supermm2
package wiki
package routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import goahead.supermm2.wiki.models.{Admin, LoginForm}
import akka.pattern._
import goahead.supermm2.wiki.actors.LoginActor
import goahead.supermm2.jmodel._

final case class AccountRoute(webRoot: String, actors: Actors) extends Supermm2Route(actors) {
  import actors._

  override def otherRoute: Route = {
    (post & path(webRoot / "login")) {
      extractForm { form: LoginForm =>
        WikiActor.ask(LoginActor.UserLogin(form.account, form.password)).mapTo[Admin]
      }.apply { r =>
        addCookie("uid", r.token).apply { json =>
          JOk(json)
        }
      }
    } ~
      (get & path(webRoot / "logout")) {
        parameter(Symbol("url").?) { t =>
          val url = t.getOrElse(ctx.settings.WebSite)
          removeCookies("uid") { _ =>
            redirect(url, StatusCodes.Found)
          }
        }
      }
  }
}
