package goahead.libs.model

final case class DBType(value: String) extends StringMID

object DBType {
  val H2 = DBType("H2")
  val MySQL = DBType("MySQL")
  val PostgreSQL = DBType("PostgreSQL")
}
