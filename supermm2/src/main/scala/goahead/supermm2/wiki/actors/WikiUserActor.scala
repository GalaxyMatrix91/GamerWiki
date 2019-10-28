package goahead.supermm2
package wiki
package actors

import akka.stream.Materializer
import goahead.libs.actor.ActorTrait
import goahead.libs.model.Magic._

import scala.concurrent.duration._
import goahead.supermm2.wiki.models._

import scala.concurrent.Future
import akka.pattern._
import goahead.libs.model.{Hidden, Randoms, Token}

object WikiUserActor {
  //人气排行榜地图  官方的
  final case object GetTopTrendingCourses extends WikiUserMessage
  final case class QueryCourseForm(course_id: String) extends WikiUserMessage
  final case class AddCourseForm(course: Course) extends WikiUserMessage
  final case class UpdateCourseForm(course: Course) extends WikiUserMessage
  final case class UploadCourseForm(course: Course) extends WikiUserMessage
  //按关卡工匠点数 降序排列 Maker  官方的
  final case object GetTopMakers extends WikiUserMessage
  final case class QueryMakerForm(maker_id: String) extends WikiUserMessage
  final case class AddMakerForm(maker: Maker) extends WikiUserMessage
  final case class UpdateMakerForm(maker: Maker) extends WikiUserMessage
  final case class UploadMakerForm(maker: Maker) extends WikiUserMessage
  final case class SignUp(account: String, password: String) extends WikiUserMessage
  //所有的毒图 网上的
  final case object GetAllPoisonCourses extends WikiUserMessage
  //按对战积分 降序排列 Maker 官方的
  final case object GetAllMakersByVersusRatingScore extends WikiUserMessage
}

final class WikiUserActor(ctx: Context)(implicit mat: Materializer) extends ActorTrait {
  import WikiUserActor._
  import ctx._

  override def preStart():Unit = {
    context.setReceiveTimeout(1.hours)
  }
  override def postStop():Unit = {}

  override def receive: Receive = {
    case GetTopTrendingCourses                => getTopTrendingCourses.pipeTo(sender())
    case form: QueryCourseForm                => findCourseByCourseId(form).pipeTo(sender())
    case form: UploadCourseForm               => uploadCourse(form).pipeTo(sender())
    case GetAllPoisonCourses                  => getAllPoisonCourses.pipeTo(sender())
    case GetTopMakers                         => getTopMakers.pipeTo(sender())
    case form: QueryMakerForm                 => findMakerByMakerId(form).pipeTo(sender())
    case form: UploadMakerForm                => uploadMaker(form).pipeTo(sender())
    case GetAllMakersByVersusRatingScore      => getAllMakersByVersusRatingScore.pipeTo(sender())
    //case form: SignUp                       => signUp(form).pipeTo(sender())           // 注册Admin
  }

  def signUp(form: SignUp): Future[Admin] = {
    val adminRole = Role(
      id = RoleID(0),
      token = Token.millis("ROLE"),
      adminId = AdminID(0),
      name = "Admin",
      isAdmin = true,
      remark = Some("Admin Role")
    )
    val q = RoleDao.add(adminRole).flatMap { r =>
      val salt = Randoms.genString(24)
      val password = form.password
      val hex = password.bytes.sha256(salt.bytes).hex
      val adminAccount = Admin(
        id = AdminID(0),
        token = Token.millis("AD"),
        roleID = r.id,
        account = form.account,
        nonce = Randoms.genString(12),
        salt = Hidden(salt),
        password = Hidden(hex),
        remark = Some("Admin")
      )
      AdminDao.add(adminAccount)
    }
    DB.runt(q)
  }

  def getAllMakersByVersusRatingScore: Future[Makers] = {
    DB.run(MakerDao.getAllMakersByVersusRatingScoreDesc()).map {
      makers =>
      Makers(makers)
    }
  }

  def getAllPoisonCourses: Future[Courses] = {
    DB.run(CourseDao.getAllPoisonCourses).map {
      courses =>
      Courses(courses)
    }
  }

  def getTopTrendingCourses: Future[Courses] = {
    DB.run(CourseDao.getAllCourses()).map {
      courses =>
      Courses(courses)
    }
  }

  def findCourseByCourseId(form: QueryCourseForm): Future[Option[Course]] =
    DB.run(CourseDao.findByCourseId(form.course_id))

  def uploadCourse(form: UploadCourseForm): Future[Course] = {
    val q = CourseDao.findByCourseId(form.course.course_id).flatMap {
      case Some(course) => CourseDao.insertOrUpdate(form.course)
      case None         => CourseDao.add(form.course)
    }
    DB.runt(q)
  }

  def getTopMakers: Future[Makers] =
    DB.run(MakerDao.getAllMakersByPointsDesc()).map { makers =>
      Makers(makers)
    }

  def findMakerByMakerId(form: QueryMakerForm): Future[Option[Maker]] =
    DB.run(MakerDao.findByMakerId(form.maker_id))

  def uploadMaker(form: UploadMakerForm): Future[Maker] = {
    val q = MakerDao.findByMakerId(form.maker.maker_id).flatMap {
      case Some(maker) => MakerDao.insertOrUpdate(form.maker)
      case None        => MakerDao.add(form.maker)
    }
    DB.runt(q)
  }

}
