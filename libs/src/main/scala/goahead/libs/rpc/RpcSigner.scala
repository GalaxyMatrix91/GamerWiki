package goahead.libs.rpc

import goahead.libs.model.Magic._
import goahead.libs.model._

// RPC 签名信息
trait RpcSigner {

  // 签名类型
  def signType: SignType

  // 开发者 ID, 如果不需要签名，就返回 None
  def developer: Option[DeveloperID]

  // 用来签名, 返回 Hex 如果返回 None，就不签名
  def sign(body: String): Option[String]
}

object RpcSigner {

  // 不用签名
  object Empty extends RpcSigner {
    def signType: SignType = SignType.None
    def developer: Option[DeveloperID] = None
    def sign(body: String): Option[String] = None
  }

  // 使用 HmacSHA256 签名
  final class HmacSHA256(dev: DeveloperID, key: KeyID) extends RpcSigner {
    def signType: SignType = SignType.HmacSHA256
    def developer: Option[DeveloperID] = Some(dev)
    def sign(body: String): Option[String] = Some(body.bytes.sha256(key.value.bytes).hex)
  }

  def HmacSHA256(developerId: DeveloperID, key: KeyID): RpcSigner = new HmacSHA256(developerId, key)
}
