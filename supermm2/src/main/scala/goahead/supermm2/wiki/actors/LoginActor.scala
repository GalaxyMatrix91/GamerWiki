package goahead.supermm2
package wiki.actors

import akka.actor._
import akka.stream.Materializer
import goahead.libs.actor.ActorTrait
import goahead.libs.model.Hidden
import goahead.supermm2.wiki.models._
import akka.pattern._

import scala.concurrent.Future
import goahead.libs.model.Magic._

import scala.util.{Failure, Success}

object LoginActor {
  final case class UserLogin(account: String, password: Hidden) extends LoginMessage
}
final private[wiki] class LoginActor(ctx: Context)(implicit mat: Materializer) extends ActorTrait {
  import LoginActor._
  import ctx._

  override def receive: Receive = {
    case req: UserLogin               => userLogin(req).pipeTo(sender())
    case req: LoginChildMessage       => handleChildMessage(req, sender())
    case Terminated(ref)              => log.info("Terminated {}", ref)
  }

  def userLogin(req: UserLogin): Future[Admin] = {
    log.debug("UserLogin: {}", req)
    DB.run(AdminDao.find(req.account))
      .flatMap{
        case Some((admin, role)) =>
            val hex = req.password.value.bytes.sha256(admin.salt.value.bytes).hex
            log.debug("Source: {} Target: {}", hex, admin.password.value)
            if (hex == admin.password.value) {
              val props = Props(new LoginChildActor(ctx, admin, role))
              Future.fromTry(retryActor(props, admin.token.value, 3)).map(_ => admin)
            } else {
              Future.failed(Errors.LoginError.extra(req.account))
            }
        case None => Future.failed(Errors.LoginError.extra(req.account))
      }
      .andThen {
        case t => log.debug("UserLoginReply: {}", t)
      }
  }

  def handleChildMessage(message: LoginChildMessage, s: ActorRef): Unit = {
    val name = message.token.value
    context.child(name) match {
      case Some(ref) => ref.tell(message, s)
      case None =>
        DB.run(AdminDao.findByToken(message.token)).foreach {
          case Some((admin, role)) =>
            val props = Props(new LoginChildActor(ctx, admin, role))
            retryActor(props, name, 3) match {
              case Success(ref) => ref.tell(message, s)
              case Failure(ex)  => s ! Status.Failure(Errors.LoginError.extra(name))
            }
          case None => s ! Status.Failure(Errors.LoginError.extra(name))
        }
    }
  }
}
