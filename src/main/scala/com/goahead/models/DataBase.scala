package com.goahead.models

import com.goahead.Tables
import com.goahead.util.db.DatabaseConnector

import scala.concurrent.ExecutionContext


final class DataBase(val databaseConnector: DatabaseConnector, val executionContext: ExecutionContext) extends Tables {

}
