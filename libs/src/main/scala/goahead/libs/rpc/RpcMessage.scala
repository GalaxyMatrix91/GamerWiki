package goahead.libs.rpc

import scala.concurrent.duration._
import io.circe._
import goahead.libs.model._
import goahead.libs.model.Magic._
import goahead.libs.json.Jsons
// 消息头(为了减少消息大小，裁剪了一些字段, 并使用简写)
final case class RpcHeader(
    token: Token, // 消息的唯一标识
    method: RpcAction, // 调用的方法, 采用 Service.Func 格式
    at: Long, // 消息创建时间
    dev: Option[DeveloperID] = None, // 开发者 ID  dev 和 sign 是成对出现的
    sign: Option[String] = None, // 对 body 的签名
    // 下面两个消息只有是 ASK 模式时才会有,
    to: Option[String] = None, // 返回的主题, 如果为 None，则标识无需返回
    exp: Option[Long] = None // 过期时间, 一般是 Ask 请求时才会有
)

// 必须有响应的消息, 为了签名方便，这里使用 String 保存，其实是一个 Json 的 string
final case class RpcMessage(header: RpcHeader, body: String = "") {

  // 唯一标识
  def token: Token = header.token

  // 方法
  def method: RpcAction = header.method

  // 服务名
  def service: String = header.method.value.takeWhile(_ != '.')

  // 服务方法
  def func: String = header.method.value.dropWhile(_ != '.').tail

  // 超时时间
  def timeout: FiniteDuration = header.exp.fold(0L)(r => r - header.at).millis

  // 耗时多少
  def used(now: Long = System.currentTimeMillis()): FiniteDuration = (now - header.at).millis

  // 是否是 Ask 消息
  def isAsk: Boolean = header.exp.nonEmpty

  // 是否是 Post 消息
  def isPost: Boolean = !isAsk

  // 是否过期了
  def isExpired: Boolean = isAsk && header.exp.get < System.currentTimeMillis()

}

object RpcMessage {

  // 普通消息(需要返回)
  def ask(method: RpcAction, body: String)(implicit signer: RpcSigner, timeout: FiniteDuration): RpcMessage = {
    val token = Token.date("ASK")
    val at = System.currentTimeMillis()
    val exp = at + timeout.toMillis
    val sign = signer.sign(body)
    val header = RpcHeader(token, method, at, signer.developer, sign, exp = Some(exp)) // to 在请求的时候填入
    RpcMessage(header, body)
  }

  // POST 消息(无需返回)
  def post(method: RpcAction, body: String)(implicit signer: RpcSigner): RpcMessage = {
    val token = Token.date("POST")
    val at = System.currentTimeMillis()
    val sign = signer.sign(body)
    val header = RpcHeader(token, method, at, signer.developer, sign)
    RpcMessage(header, body)
  }

  // ASK 消息(需要返回)
  def askJson[T](
      method: RpcAction,
      body: T)(implicit signer: RpcSigner, timeout: FiniteDuration, e: Encoder[T]): RpcMessage =
    ask(method, Jsons.stringify(body))

  // POST 消息(无需返回)
  def postJson[T](method: RpcAction, body: T)(implicit signer: RpcSigner, e: Encoder[T]): RpcMessage =
    post(method, Jsons.stringify(body))
}

// 返回信息
final private[rpc] case class RpcReply(
    sn: Token, // 唯一标识
    body: Option[String] = None, // 返回内容
    error: Option[String] = None // 返回异常
)

object RpcReply {

  def success(token: Token, json: Json): RpcReply = RpcReply(token, Some(Jsons.stringify(json)), None)

  def failure(token: Token, ex: Throwable): RpcReply = RpcReply(token, None, Some(Jsons.stringify(ex.base)))
}

// 失败消息
final case class TimeoutReply(token: Token)
