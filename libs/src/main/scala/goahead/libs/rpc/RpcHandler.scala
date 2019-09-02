package goahead.libs.rpc

import scala.concurrent.{ExecutionContext, Future}
import scala.language.implicitConversions
import io.circe._
import goahead.libs.model._

// 处理服务请求, 路由到其他节点上
trait RpcHandler {

  final type JAction = PartialFunction[RpcAction, Json => Future[Json]]

  // 服务名
  def service: String

  // 处理 Json 消息
  def rpcHandler: JAction

  // 将 Model 转成 Json
  implicit final def Model2Json[A](a: A)(implicit d: Encoder[A]): Json = d(a)

  // 将 Future[A] 转成 Future[Json]
  implicit final def FutureModel2Json[A](a: Future[A])(implicit ec: ExecutionContext, d: Encoder[A]): Future[Json] =
    a.map(r => d(r))

}
