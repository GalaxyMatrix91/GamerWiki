package goahead.supermm2
package wiki.actors

import akka.actor.ReceiveTimeout
import goahead.libs.actor.ActorTrait
import goahead.libs.model.Token
import goahead.supermm2.wiki.models._
import akka.pattern._
import scala.concurrent.Future
import scala.concurrent.duration._

object LoginChildActor {
  final case class FindAdmin(token: Token) extends LoginChildMessage
  final case class ReloadAdmin(token: Token) extends LoginChildMessage
  final case class CheckPerm(token: Token, perms: Seq[PermID]) extends LoginChildMessage
}
final private[wiki] class LoginChildActor(ctx: Context, admin: Admin, role: Role) extends ActorTrait {
  import LoginChildActor._

  override def preStart(): Unit = {
    context.setReceiveTimeout(1.hours)
  }

  override def receive: Receive = {
    case _: FindAdmin => sender() ! admin
    case req: CheckPerm => checkPerm(req).pipeTo(sender())
    case _: ReloadAdmin => context.stop(self)
    case ReceiveTimeout => context.stop(self)
  }

  def checkPerm(req: CheckPerm): Future[Boolean] = {
    if(role.isAdmin) Future.successful(true)
    else Future.failed(Errors.NoPermission)
  }
}
