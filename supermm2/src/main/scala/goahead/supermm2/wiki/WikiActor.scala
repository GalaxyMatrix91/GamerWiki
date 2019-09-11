package goahead.supermm2
package wiki

import akka.actor.{ActorRef, Props, Status}
import akka.stream.Materializer
import goahead.libs.actor.ActorTrait
import goahead.supermm2.wiki.actors.LoginActor
import goahead.supermm2.wiki.models.{LoginMessage, WikiUserMessage}

import scala.util.{Failure, Success}

final class WikiActor(ctx: Context)(implicit mat: Materializer) extends ActorTrait {
  import ctx._

  val loginActor = context.watch(context.actorOf(Props(new LoginActor(ctx)), "login"))

  override def receive: Receive = {
    case req: LoginMessage    => loginActor.forward(req)
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
