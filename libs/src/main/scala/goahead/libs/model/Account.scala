package goahead.libs.model

// 三户：不一定非要使用这三个字段，也可以使用其他字段，只要满足三户模型即可
// 客户
final case class CustomerID(value: Long) extends LongMID
// 用户
final case class UserID(value: Long) extends LongMID
// 账户
final case class AccountID(value: Long) extends LongMID

// 服务商
final case class ProviderID(value: Long) extends LongMID
// 代理商
final case class AgentID(value: Long) extends LongMID
// 商户
final case class MerchantID(value: Long) extends LongMID

// 日志ID
final case class LogID(value: Long) extends LongMID
