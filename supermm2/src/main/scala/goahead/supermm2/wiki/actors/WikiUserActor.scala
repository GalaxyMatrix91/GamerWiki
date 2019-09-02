package goahead.supermm2
package wiki
package actors

import akka.stream.Materializer
import goahead.libs.actor.ActorTrait
import scala.concurrent.duration._
import goahead.supermm2.wiki.models._
import scala.concurrent.Future
import akka.pattern._

object WikiUserActor {
  final case object GetTopTrendingCourses
  final case object GetTopMakers
}
final class WikiUserActor(ctx: Context)(implicit mat: Materializer) extends ActorTrait {
  import WikiUserActor._
  import ctx._

  override def preStart():Unit = {
    context.setReceiveTimeout(1.hours)
  }
  override def postStop():Unit = {}

  override def receive: Receive = {
    case GetTopTrendingCourses   => getTopTrendingCourses().pipeTo(sender())
  }

  def getTopTrendingCourses(): Future[Seq[Course]] = {
    DB.run(CourseDao.getAllCourses())
  }
}
