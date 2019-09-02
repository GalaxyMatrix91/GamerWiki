package goahead.libs.rpc

import java.time.Duration
import java.util.concurrent.atomic.AtomicLong
import akka.actor._
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core._
import scala.collection.concurrent.TrieMap

final class RpcContext(val host: String, val port: Int, password: Option[String] = None)(implicit system: ActorSystem)
    extends AutoCloseable {

  // 连接池
  private val redisClient = buildRedis()
  private val index = new AtomicLong(0)
  private val instances = TrieMap.empty[Long, AutoCloseable]

  def name: String = host + "-" + port

  // 创建客户端, 由于使用了异步机制，每个 redis 只需要创建一个 client 和 server 即可
  def newClient(): RpcClient = {
    val id = index.incrementAndGet()
    val props = Props(new RpcClientActor(redisClient))
    val ref = system.actorOf(props, s"rpc-client-$name-$id")
    val ret = new RpcClient(id, ref)(this)
    instances.put(id, ret)
    ret
  }

  // 创建服务器
  def newServer(handlers: Seq[RpcHandler]): RpcServer = {
    val id = index.incrementAndGet()
    val props = Props(new RpcServerActor(redisClient, handlers))
    val ref = system.actorOf(props, s"rpc-server-$name-$id")
    val ret = new RpcServer(id, ref)(this)
    instances.put(id, ret)
    ret
  }

  // 新建连接
  def mkConnect: StatefulRedisConnection[String, String] = redisClient.connect()

  // 关闭
  def close(): Unit = {
    instances.values.foreach(_.close())
    instances.clear()
    redisClient.shutdown()
  }

  private def buildRedis(): RedisClient = {
    val redisUri = RedisURI.Builder.redis(host).withPort(port).withTimeout(Duration.ofMinutes(10))
    password.foreach(r => redisUri.withPassword(r))
    RedisClient.create(redisUri.build())
  }

  private[rpc] def remove(id: Long): Option[AutoCloseable] = {
    instances.remove(id)
  }
}

object RpcContext {

  def newContext(host: String, port: Int, password: Option[String] = None)(implicit system: ActorSystem): RpcContext = {
    new RpcContext(host, port, password)
  }
}
