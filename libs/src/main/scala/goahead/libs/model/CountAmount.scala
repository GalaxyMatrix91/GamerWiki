package goahead.libs.model

// 次数与金额
final case class CountAmount(count: Int, amount: Price) {
  def +(that: CountAmount): CountAmount = CountAmount(count + that.count, amount + that.amount)
  def -(that: CountAmount): CountAmount = CountAmount(count - that.count, (amount - that.amount).r)
}

object CountAmount {

  val Z: CountAmount = new CountAmount(0, Price.Z)

  def apply(count: Int, amount: Double): CountAmount = new CountAmount(count, Price(amount))
}
