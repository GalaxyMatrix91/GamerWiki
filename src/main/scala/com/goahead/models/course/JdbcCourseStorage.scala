package com.goahead.models.course

import com.goahead.util.db.DatabaseConnector

import scala.concurrent.Future


final class JdbcCourseStorage(val databaseConnector: DatabaseConnector) extends CourseTable {
  import databaseConnector._
  import databaseConnector.profile.api._

  val courseTable = TableQuery[Coursetables]

  def getTrendingCourse():Future[Seq[Course]] = {
    db.run(courseTable.result)
  }

  def findByCourseId(courseId: String):Future[Option[Course]] = {
    db.run(courseTable.filter(_.id === courseId).result.headOption)
  }

  def addNewTrendingCourse(course: Course):Future[Int] =
    db.run(courseTable += course)
}