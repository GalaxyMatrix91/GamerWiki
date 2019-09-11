package goahead.supermm2.wiki.models

import goahead.libs.model.IntMID

// admin user
final case class AdminID(value: Int) extends IntMID
// role
final case class RoleID(value: Int) extends IntMID
// permission
final case class PermID(value: Int) extends IntMID

object PermID {
  val Root = PermID(0)
}