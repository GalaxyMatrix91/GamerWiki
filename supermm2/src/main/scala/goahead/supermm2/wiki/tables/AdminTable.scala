package goahead.supermm2.wiki.tables

import goahead.libs.model.{Hidden, StateID, Token}
import goahead.supermm2.DBSchema
import goahead.supermm2.wiki.models._
import profile.api._

import scala.concurrent.ExecutionContext

final class AdminTable(stag: Tag)(implicit schema: DBSchema)
  extends Table[Admin](stag, Option(schema.supermm2), "admin") with BaseTable {
  def id = column[AdminID]("id", O.PrimaryKey, O.AutoInc)
  def role_id = column[RoleID]("role_id")
  def account = column[String]("account", O.Unique)
  def nonce = column[String]("nonce")
  def salt = column[Hidden]("salt")
  def password = column[Hidden]("password")
  def * = (id, token, role_id, account, nonce, salt, password, remark, state).mapTo[Admin]
  def account_index = index(s"idx_${tableName}_account", account, unique = false)
}

final class AdminDao(implicit schema: DBSchema, ec: ExecutionContext) {
  val table = TableQuery[AdminTable]
  val auto = table.returning(table.map(_.id)).into { case (p, id) => p.copy(id = id) }
  val RoleTable = TableQuery[RoleTable]

  def add(admin: Admin): DBIO[Admin] = auto += admin

  def find(account: String): DBIO[Option[(Admin, Role)]] = {
    val q = for {
      r <- table if r.account === account && r. state === StateID.Enable
      role <- RoleTable if role.id === r.role_id && role.admin_id === r.id
    } yield (r, role)
    q.result.headOption
  }

  def findByToken(token: Token): DBIO[Option[(Admin, Role)]] = {
    val q = for {
      r <- table if r.token === token && r. state === StateID.Enable
      role <- RoleTable if role.id === r.role_id && role.admin_id === r.id
    } yield (r, role)
    q.result.headOption
  }
}
