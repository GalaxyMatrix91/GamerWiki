package goahead.supermm2.wiki.tables

import java.time.ZonedDateTime
import profile.api._
import goahead.libs.model._

trait BaseTable { self: Table[_] =>
  // 一般来说每个实体表都有一个 token ，用于唯一标识这个对象，通过外部访问时，使用该字段，而内部都通过主键访问
  def token = column[Token]("token", O.Unique)
  def sn = column[SN]("sn") // SN 不是全局唯一，只是在某个范围内是唯一的
  def remark = column[Option[String]]("remark")
  def state = column[StateID]("state_id", O.Default(StateID.Enable))
  // 所有表都会自动添加以下 2 个字段，如果需要在代码中使用它们，请显式引用, 如果不需要，就可以忽略不计
  def created_at = column[ZonedDateTime]("created_at", O.SqlType(TimestampWithZone))
  def updated_at = column[ZonedDateTime]("updated_at", O.SqlType(TimestampWithZone))
  // 这里不要定义任何索引、外键等字段，否则因为会自动生成 SQL 脚本的

}
