package goahead.supermm2.wiki.tables

import profile.api._
import goahead.libs.model._
import goahead.supermm2.wiki.models._
import goahead.supermm2.DBSchema

import scala.concurrent.ExecutionContext

final class RoleTable(stag: Tag)(implicit schema: DBSchema)
  extends Table[Role](stag, Option(schema.supermm2), "role") with BaseTable {
  def id = column[RoleID]("id", O.PrimaryKey, O.AutoInc)
  def admin_id = column[AdminID]("admin_id")
  def name = column[String]("name")
  def is_admin = column[Boolean]("is_admin")

  def * = (id, token, admin_id, name, is_admin, remark, state).mapTo[Role]

  def admin_id_index = index(s"idx_${tableName}_admin_id", admin_id, unique = false)
}

final class RoleDao(implicit schema: DBSchema, ec: ExecutionContext) {
  val table = TableQuery[RoleTable]
  val auto = table.returning(table.map(_.id)).into { case (p, id) => p.copy(id = id) }

  def add(role: Role): DBIO[Role] = auto += role
}
