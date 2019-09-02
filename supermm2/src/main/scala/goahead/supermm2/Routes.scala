package goahead.supermm2

import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import goahead.libs.model.{HttpAction, TradeID}

final class Routes(webRoot: String, actors: Actors)(implicit mat: ActorMaterializer) extends Supermm2Route(actors) {
  //all routes
  override val Routes = Seq(new wiki.WikiRoute(webRoot, actors))

  def route: Route = gateway(joinHandler) ~ joinRoute

  //universal message
  private def gateway(handler: JAction): Route = (post & path(webRoot / "gateway")) {
    fetchMessage { wrap =>
      handler(wrap.method)(wrap)
    }
  }

  //get Message info, need to login first
  def fetchMessage: Directive1[Supermm2Message] = {
    extractRequestContext.flatMap { ctx =>
      parameters(Symbol("id").?, Symbol("method").?).tflatMap {
        case (Some(id), Some(method)) =>
          val f = for {
            body <- ctx.request.entity.dataBytes.runFold("")(_ ++ _.utf8String)
            //TODO 需要校验权限
          } yield Supermm2Message(TradeID(id), HttpAction(method), body)
          onSuccess(f)
        case _ => reject(Errors.WrongParam.extra("id or method"))
      }
    }
  }
}