package goahead.libs.rpc

import java.util.concurrent.TimeoutException
import akka.actor._
import io.circe.Json
import akka.pattern._
import io.lettuce.core.RedisClient
import io.lettuce.core.pubsub.RedisPubSubListener
import goahead.libs.actor.ActorTrait
import goahead.libs.json.Jsons
import goahead.libs.model._
import scala.collection.concurrent.TrieMap
import scala.compat.java8.FunctionConverters._
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

// PRC 客户端，负责调用远程的 RPC
final private[rpc] class RpcClientActor(redisClient: RedisClient) extends ActorTrait with Timers {

  // 随机的回复主题
  val ReplyTopic = Token.millis("TO", 4)
  // 保存返回的值
  val askMap = TrieMap.empty[Token, Promise[Json]]
  // 连接
  val subConnect = redisClient.connectPubSub()
  val pubConnect = redisClient.connectPubSub()
  val pub = pubConnect.async() // 使用异步方式

  val logError = asJavaBiFunction[java.lang.Long, Throwable, java.lang.Long] {
    case (_, u) => if (u != null) { log.error(u, "RpcClientPubError:") }; 1L
  }

  val listener = new RedisPubSubListener[String, String] {
    def message(channel: String, message: String) = {
      log.debug("RpcClientActor.onMessage: {} {}", channel, message)
      Jsons.from[RpcReply](message) match {
        case Success(reply) if reply.error.isEmpty =>
          askMap.get(reply.sn).foreach { r =>
            reply.body.fold(r.trySuccess(Json.Null))(j => r.tryComplete(Jsons.parse(j)))
          }
        case Success(reply) =>
          askMap.get(reply.sn).foreach { r =>
            val ex = Jsons.from[GoAheadException](reply.error.get) match {
              case Success(ex) => ex
              case Failure(ex) => ex
            }
            r.tryFailure(ex)
          }
        case Failure(ex) => log.error(ex, "RpcClient.onMessageError:{} {}", channel, message)
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

  override def preStart(): Unit = {
    subConnect.addListener(listener)
    subConnect.sync().subscribe(ReplyTopic.value)
    log.info("ReplyTopic: {}", ReplyTopic)
  }

  override def postStop(): Unit = {
    subConnect.close()
    pubConnect.close()
    askMap.clear()
  }

  def receive: Receive = {
    case req: RpcMessage if req.isAsk => askMessage(req).pipeTo(sender())
    case req: RpcMessage              => postMessage(req)
    case req: TimeoutReply            => timeoutReply(req)
  }

  // 需要有返回值
  def askMessage(req: RpcMessage): Future[Json] = {
    // 先埋点
    val result = Promise[Json]
    askMap.put(req.token, result)
    // 再发送请求
    val real = req.copy(header = req.header.copy(to = Some(ReplyTopic.value)))
    val value = Jsons.stringify(real)
    // 再发消息
    log.debug("AskMessage: {}", real)
    pub.publish(req.service, value).handle(logError)
    // 开启定时任务，处理超时
    timers.startSingleTimer(real.token, TimeoutReply(real.token), real.timeout)
    result.future.andThen {
      case t =>
        log.debug("AskMessageResult: {}", req)
        timers.cancel(real.token) // 取消任务
        askMap.remove(real.token).foreach { _ =>
          log.debug("{} using {} with {}", real.header.method, real.used(), t)
        }
    }
  }

  // 无需返回值
  def postMessage(req: RpcMessage): Unit = {
    val json = Jsons.toJson(req)
    val value = Jsons.stringify(json)
    pub.publish(req.service, value).handle(logError)
    log.debug("RpcPostMessage {}", req)
  }

  def timeoutReply(req: TimeoutReply): Unit = {
    log.error("RpcClientTimeout: {}", req.token)
    askMap.get(req.token).foreach(_.tryFailure(new TimeoutException(s"Timeout with ${req.token.value}")))
  }
}
