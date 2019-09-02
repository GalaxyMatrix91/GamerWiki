package goahead.libs.model

/**
 * 常用的 ID
 */
final case class IntID(value: Int) extends IntMID
final case class LongID(value: Long) extends LongMID
final case class StringID(value: String) extends StringMID
final case class ID(value: Long) extends LongMID

// 品牌 & 门店
final case class BrandID(value: Long) extends LongMID
final case class ShopID(value: Long) extends LongMID

// 密钥
final case class KeyID(value: String) extends StringMID
// AppID 标识
final case class AppID(value: String) extends StringMID
// 用户标识
final case class OpenID(value: String) extends StringMID
// UnionID
final case class UnionID(value: String) extends StringMID
// 商户号
final case class MchID(value: String) extends StringMID
// 密码
final case class AppSecret(value: String) extends StringMID
// HttpAction
final case class HttpAction(value: String) extends StringMID
// RpcAction
final case class RpcAction(value: String) extends StringMID
// 开发者 ID
final case class DeveloperID(value: String) extends StringMID
