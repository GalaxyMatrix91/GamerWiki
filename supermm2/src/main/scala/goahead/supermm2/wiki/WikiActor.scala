package goahead.supermm2
package wiki

import akka.actor.{ActorRef, Props, Status}
import akka.stream.Materializer
import goahead.libs.actor.ActorTrait
import goahead.supermm2.wiki.actors.{LoginActor, OssActor}
import goahead.supermm2.wiki.models.{LoginMessage, OssMessage, WikiUserMessage}

import scala.util.{Failure, Success}

final class WikiActor(ctx: Context)(implicit mat: Materializer) extends ActorTrait {
  import ctx._

  val ossActor = context.watch(context.actorOf(OssActor.props(ctx).get, "oss"))
  val loginActor = context.watch(context.actorOf(Props(new LoginActor(ctx)), "login"))

  override def receive: Receive = {
    case req: LoginMessage    => loginActor.forward(req)
    case req: OssMessage      => ossActor.forward(req)
    case req: WikiUserMessage => handleWikiUserMessage(req, sender())
  }

  def handleWikiUserMessage(req: WikiUserMessage, s: ActorRef): Unit = {
    val name = "wiki_user"
    context.child(name) match {
      case Some(ref) => ref.tell(req, s)
      case None =>
        retryActor(Props(new actors.WikiUserActor(ctx)), name, 3) match {
          case Success(ref) => ref.tell(req, s)
          case Failure(ex)  => s ! Status.Failure(Errors.NotFound.extra(ex))
        }
    }
  }


}
