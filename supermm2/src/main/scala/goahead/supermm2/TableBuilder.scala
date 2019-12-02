package goahead.supermm2

import java.io.File
import goahead.supermm2.wiki.tables._
import com.typesafe.scalalogging.Logger
import slick.lifted.TableQuery

object TableBuilder {
  val logger = Logger(this.getClass)

  implicit val schema = DBSchema()

  def All: Seq[TQ] =
    Seq(
      TableQuery[wiki.tables.AdminTable],
      TableQuery[wiki.tables.RoleTable],
      TableQuery[wiki.tables.MakerTable],
      TableQuery[wiki.tables.CourseTable],
      TableQuery[wiki.tables.GameInfoTable]
    )
  def main(args: Array[String]): Unit = {
    println("Gen SQL File")

    val home = new File("doc/db")
    createSql(All, home, "smm2_wiki")
    dropSql(All, home, "smm2_wiki")
    dumpTrigger(All, home, "smm2_wiki") { sql =>
      if (sql.indexOf("updated_at") > 0) Some("updated_at") else None
    }
  }
 }
