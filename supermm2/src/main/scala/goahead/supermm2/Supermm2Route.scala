package goahead.supermm2

import akka.http.scaladsl.server.Directive1
import akka.util.Timeout
import goahead.libs.http.{CookieConfig, JActionSupport}
import goahead.libs.model.{HttpAction, Token, TradeID}
import goahead.supermm2.wiki.actors.LoginChildActor
import akka.pattern._
import goahead.supermm2.wiki.models.Admin
import goahead.supermm2.jmodel._
import scala.concurrent.duration._
import scala.util.Failure

final case class Supermm2Message(
                                id: TradeID,  //request ID
                                method: HttpAction,   //http method
                                body: String  //请求内容，UTF-8 格式, 服务可以将它反序列化成想要的格式
                                )
//通用类
abstract class Supermm2Route(actors: Actors) extends JActionSupport[Supermm2Message] {
  import actors._

  implicit val DefaultTimout = Timeout(30.seconds)
  implicit val EC = ctx.threads.db
  implicit val langs = ctx.langs
  implicit val cookieConfig = CookieConfig.from(ctx.settings.CookieConfig).get

  final def auth: Directive1[Admin] = {
    cookieOf[Token]("uid").flatMap {
      case Some(uid) =>
        extractRequestContext.flatMap { ctx =>
          val f = WikiActor.ask(LoginChildActor.FindAdmin(uid)).mapTo[Admin].andThen {
            case Failure(_) => ctx.request.discardEntityBytes()
          }
          onSuccess(f)
        }
      case None => reject(Errors.NoLogin)
    }
  }
}