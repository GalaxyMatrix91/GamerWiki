package goahead.supermm2.wiki.tables

/*
case class Course(
                   course_id: String,
                   course_name: String,
                   course_type: String,
                   course_image_url: String,
                   course_detail_url: String,
                   num_likes: Long,
                   times_played: Long,
                   clear_check_time: String,
                   creator: Creator,
                   tag1: String,
                   tag2: String,
                   world_record_holder: Player,
                   wr_time: Option[String],
                   first_player: Player,
                   clear_rate: String,
                   completed: Long,
                   total_times: Long,
                   oss_image_url: Option[String]
                 )
 */
import profile.api._
import goahead.supermm2.DBSchema
import goahead.supermm2.wiki.models._

import scala.concurrent.ExecutionContext

final class CourseTable(stag: Tag)(implicit schema: DBSchema) extends Table[Course](stag, Option(schema.supermm2), "courses") {
  def id = column[String]("course_id", O.PrimaryKey)

  def courseName = column[String]("course_name")

  def courseType = column[String]("course_type")

  def courseImageUrl = column[String]("course_image_url")

  def courseDetailUrl = column[String]("course_detail_url")

  def numLikes = column[Long]("num_likes")

  def timesPlayed = column[Long]("times_played")

  def clearCheckTime = column[String]("clear_check_time")

  def creatorName = column[String]("creator_name")

  def creatorAvatar = column[String]("creator_avatar")

  def creatorNationality = column[String]("creator_nationality")

  def tag1 = column[String]("tag1")

  def tag2 = column[String]("tag2")

  def wrHolderName = column[Option[String]]("wr_holder_name")

  def wrHolderAvatar = column[Option[String]]("wr_holder_avatar")

  def wrHolderNationality = column[Option[String]]("wr_holder_nationality")

  def wrTime = column[Option[String]]("wr_time")

  def firstName = column[Option[String]]("first_name")

  def firstAvatar = column[Option[String]]("first_avatar")

  def firstNationality = column[Option[String]]("first_nationality")

  def clearRate = column[String]("clear_rate")

  def completed = column[Long]("completed")

  def totalTimes = column[Long]("total_times")

  def ossImageUrl = column[Option[String]]("oss_image_url")

  def ossImageDetailUrl = column[Option[String]]("oss_image_detail_url")

  def * = (id, courseName, courseType, courseImageUrl, courseDetailUrl, numLikes, timesPlayed,
    clearCheckTime, (creatorName, creatorAvatar, creatorNationality), tag1, tag2, (wrHolderName, wrHolderAvatar, wrHolderNationality),
    wrTime, (firstName, firstAvatar, firstNationality), clearRate, completed, totalTimes, ossImageUrl, ossImageDetailUrl).shaped <>
    ( {
      case (id, courseName, courseType, courseImageUrl, courseDetailUrl, numLikes, timesPlayed,
      clearCheckTime, t1, tag1, tag2, t2, wrTime, t3, clearRate, completed, totalTimes, ossImageUrl, ossImageDetailUrl) =>
        Course(id, courseName, courseType, courseImageUrl, courseDetailUrl, numLikes, timesPlayed,
          clearCheckTime, Creator(t1._1, t1._2, t1._3), tag1, tag2, Player(t2._1, t2._2, t2._3), wrTime, Player(t3._1, t3._2, t3._3), clearRate, completed, totalTimes, ossImageUrl, ossImageDetailUrl)
    }, {
      c: Course =>
        Some((c.course_id, c.course_name, c.course_type, c.course_image_url, c.course_detail_url, c.num_likes, c.times_played, c.clear_check_time, (c.creator.creator_name,
          c.creator.creator_avatar, c.creator.creator_nationality), c.tag1, c.tag2, (c.world_record_holder.name, c.world_record_holder.avatar, c.world_record_holder.nationality),
          c.wr_time, (c.first_player.name, c.first_player.avatar, c.first_player.nationality), c.clear_rate, c.completed, c.total_times, c.oss_image_url, c.oss_image_detail_url))
    })
}

final class CourseDao(implicit schema: DBSchema, ec: ExecutionContext) {
  val table = TableQuery[CourseTable]
  val auto = table.returning(table.map(_.id)).into { case (p, id) => p.copy(course_id = id)}

  def getAllCourses():DBIO[Seq[Course]] =
    table.sortBy(_.id).result

  def findByCourseId(course_id: String): DBIO[Option[Course]] =
    table.filter(_.id === course_id).result.headOption
}