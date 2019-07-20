
package com.goahead
package actors

import akka.actor._
import akka.pattern._
import akka.util.Timeout
import com.goahead.models.DataBase
import com.goahead.models.course.Course

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

object Supermm2Actor {
  final case object GetTrendingCourses
  final case class FindByCourseID(courseID: String)
  final case class UpdateTrendingCourse(course: Course)
  def props(db: DataBase, ec: ExecutionContext): Props = Props(new Supermm2Actor(db, ec))
}
final class Supermm2Actor(database: DataBase, ec: ExecutionContext) extends Actor with ActorLogging {
  import Supermm2Actor._
  implicit val executionContext = ec
  implicit val timeout = Timeout(30.seconds)

  override def receive: Receive = {
    case GetTrendingCourses => pipe(getTrendingCourses) to sender()
    case form: FindByCourseID     => pipe(findByCourseId(form)) to sender()
    case form: UpdateTrendingCourse => addNewTrendingCourse(form).pipeTo(sender())

  }
  private[this] def getTrendingCourses: Future[Seq[Course]] =
    database.CourseTables.getTrendingCourse()

  private[this] def findByCourseId(form: FindByCourseID):Future[Option[Course]] =
    database.CourseTables.findByCourseId(form.courseID)

  private[this] def addNewTrendingCourse(form: UpdateTrendingCourse):Future[Int] = {
      database.CourseTables.addNewTrendingCourse(form.course)
  }
}

