package goahead.libs.model

/**
 * 基本类型
 */
final case class Minute(value: Int) extends IntMID // 分钟

final case class Percentage(value: Int) extends IntMID // 百分比(整型, 0-100)

final case class Ratio(value: Double) extends DoubleMID // 比率(0 - 1.0)

final case class Hidden(value: String) extends StringMID { // 不能暴露给前端的值, 例如密码\盐等字段, 在 JSON 序列化时都会使用 **** 替换掉
  override def toString = "***"
}

// Email
final case class Email(value: String) extends StringMID

// 分辨率
final case class Size(width: Int, height: Int)

// 返回结果(Json 友好)
final case class BoolResult(result: Boolean)
