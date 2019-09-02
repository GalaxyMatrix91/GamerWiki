package goahead.supermm2.wiki

import goahead.supermm2.Context
import goahead.supermm2.wiki.tables._

trait WikiContext { self: Context =>
  //DAO
  lazy val CourseDao = new CourseDao()(schema, threads.db)
}
