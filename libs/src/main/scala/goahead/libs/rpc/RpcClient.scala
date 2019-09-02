package goahead.libs.rpc

import akka.actor._
import akka.pattern._
import com.typesafe.scalalogging.Logger
import io.circe._
import goahead.libs.json.Jsons
import goahead.libs.model.RpcAction
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{ExecutionContext, Future}

final class RpcClient(id: Long, ref: ActorRef)(implicit ctx: RpcContext) extends AutoCloseable {

  val logger = Logger(this.getClass)

  // 使用 call， 因为 ask 名称有冲突
  def call[In, Out](method: RpcAction, body: In)(
      implicit ec: ExecutionContext,
      signer: RpcSigner,
      timeout: FiniteDuration,
      e: Encoder[In],
      d: Decoder[Out]): Future[Out] = {
    val message = RpcMessage.askJson(method, body)
    ref.ask(message)(timeout).flatMap {
      case json: Json => Future.fromTry(Jsons.from[Out](json))
    }
  }

  // 不需要返回的消息
  def post[In](method: RpcAction, body: In)(implicit signer: RpcSigner, e: Encoder[In]): Unit = {
    val message = RpcMessage.postJson(method, body)
    ref ! message
  }

  def close(): Unit = {
    logger.info("RpcClientClose: {}", ref)
    ctx.remove(id)
    ref ! PoisonPill
  }
}
