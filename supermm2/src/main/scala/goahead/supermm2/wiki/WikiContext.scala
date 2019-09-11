package goahead.supermm2.wiki

import goahead.supermm2.Context
import goahead.supermm2.wiki.tables._

trait WikiContext { self: Context =>
  //DAO
  lazy val CourseDao = new CourseDao()(schema, threads.db)
  lazy val MakerDao = new MakerDao()(schema, threads.db)
  lazy val AdminDao = new AdminDao()(schema, threads.db)
  lazy val RoleDao = new RoleDao()(schema, threads.db)
}
