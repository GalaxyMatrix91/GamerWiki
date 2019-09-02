package goahead.libs.model

// 基本操作
final case class CRUD(value: String) extends StringMID

object CRUD {
  val Create = CRUD("Create")
  val Read = CRUD("Read")
  val Update = CRUD("Update")
  val Delete = CRUD("Delete")
}
