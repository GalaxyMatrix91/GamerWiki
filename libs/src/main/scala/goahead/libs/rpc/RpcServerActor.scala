package goahead.libs.rpc

import scala.concurrent.{Future, TimeoutException}
import scala.util.{Failure, Success}
import io.lettuce.core.RedisClient
import io.lettuce.core.pubsub.RedisPubSubListener
import scala.compat.java8.FunctionConverters.asJavaBiFunction
import goahead.libs.actor.ActorTrait
import goahead.libs.json.Jsons

final private[rpc] class RpcServerActor(redisClient: RedisClient, handlers: Seq[RpcHandler]) extends ActorTrait {

  // 所有的服务名
  val services = handlers.map(_.service)
  // 连接
  val subConnect = redisClient.connectPubSub()
  val pubConnect = redisClient.connectPubSub()
  val pub = pubConnect.async()

  val listener = new RedisPubSubListener[String, String] {
    def message(channel: String, message: String) = {
      log.debug("RpcServerActor.onMessage: {} {}", channel, message)
      Jsons.from[RpcMessage](message) match {
        case Success(r) if r.isExpired => // 如果过期了，就直接返回失败, 不处理了
          sendReply(r.header.to.get, RpcReply.failure(r.token, new TimeoutException(r.token.toString)))
        case Success(r) =>
          handlers.find(_.service == channel) match {
            case Some(h) =>
              Future
                .fromTry(Jsons.parse(r.body))
                .flatMap { json =>
                  h.rpcHandler(r.method)(json)
                }
                .andThen {
                  case Success(v) if r.isAsk  => sendReply(r.header.to.get, RpcReply.success(r.token, v))
                  case Failure(ex) if r.isAsk => sendReply(r.header.to.get, RpcReply.failure(r.token, ex))
                }
            case None => log.error("RpcServer.onMessageError: HandlerNotFound with {} {}", channel, message)
          }
        case Failure(ex) => log.error(ex, "RpcServer.onMessageError with {} {}", channel, message)
      }
    }

    def message(pattern: String, channel: String, message: String): Unit = {}

    def subscribed(channel: String, count: Long): Unit = {
      log.info("Subscribed: {}", channel)
    }

    def psubscribed(pattern: String, count: Long): Unit = {}

    def unsubscribed(channel: String, count: Long): Unit = {}

    def punsubscribed(pattern: String, count: Long): Unit = {}
  }

  val logError = asJavaBiFunction[java.lang.Long, Throwable, java.lang.Long] {
    case (_, u) => if (u != null) { log.error(u, "RpcClientPubError:") }; 1L
  }

  override def preStart(): Unit = {
    log.info("StartRpcServer: {}", self)
    subConnect.addListener(listener)
    subConnect.sync().subscribe(services: _*)
  }

  override def postStop(): Unit = {
    subConnect.close()
    pubConnect.close()
  }

  override def receive: Receive = PartialFunction.empty

  def sendReply(replyTo: String, reply: RpcReply): Unit = {
    val value = Jsons.stringify(reply)
    pub.publish(replyTo, value).handle(logError)
  }

}
