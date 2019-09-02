package goahead.libs.rpc

import akka.actor._
import com.typesafe.scalalogging.Logger

final class RpcServer(id: Long, ref: ActorRef)(implicit ctx: RpcContext) extends AutoCloseable {
  val logger = Logger(this.getClass)

  def close(): Unit = {
    logger.info("RpcServerClose: {}", ref)
    ctx.remove(id)
    ref ! PoisonPill
  }
}
