package com.goahead

import com.goahead.models.course.JdbcCourseStorage

import com.goahead.util.db.DatabaseConnector

import scala.concurrent.ExecutionContext


trait Tables {
  implicit def executionContext: ExecutionContext
  implicit def databaseConnector: DatabaseConnector
  lazy val CourseTables = new JdbcCourseStorage(databaseConnector)
}
