package com.goahead.models.course

import com.goahead.util.db.DatabaseConnector

/*
case class Course(
                   course_id: String,
                   course_name: String,
                   course_type: String,
                   course_image_url: String,
                   course_detail_url: String,
                   num_likes: Int,
                   times_played: Int,
                   clear_check_time: String,
                   creator_name: String,
                   creator_avatar: String,
                   creator_nationality: String,
                   tag1: String,
                   tag2: String,
                   wr_holder_name: Option[String],
                   wr_holder_avatar: Option[String],
                   wr_time: Option[String],
                   first_name: Option[String],
                   first_avatar: Option[String],
                   first_nationality: Option[String],
                   clear_rate: String,
                   completed: Int,
                   total_times: Int
                 )
 */
private[course] trait CourseTable {
  protected val databaseConnector: DatabaseConnector

  import databaseConnector.profile.api._

  class Coursetables(tag: Tag) extends Table[Course](tag, Some("supermm2_wiki"), "courses") {
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

    def * = (id, courseName, courseType, courseImageUrl, courseDetailUrl, numLikes, timesPlayed,
      clearCheckTime, (creatorName, creatorAvatar, creatorNationality), tag1, tag2, (wrHolderName, wrHolderAvatar, wrHolderNationality),
      wrTime, (firstName, firstAvatar, firstNationality), clearRate, completed, totalTimes, ossImageUrl).shaped <>
      ( {
        case (id, courseName, courseType, courseImageUrl, courseDetailUrl, numLikes, timesPlayed,
        clearCheckTime, t1, tag1, tag2, t2, wrTime, t3, clearRate, completed, totalTimes, ossImageUrl) =>
          Course(id, courseName, courseType, courseImageUrl, courseDetailUrl, numLikes, timesPlayed,
            clearCheckTime, Creator(t1._1, t1._2, t1._3), tag1, tag2, Player(t2._1, t2._2, t2._3), wrTime, Player(t3._1, t3._2, t3._3), clearRate, completed, totalTimes, ossImageUrl)
      }, {
        c: Course =>
          Some((c.course_id, c.course_name, c.course_type, c.course_image_url, c.course_detail_url, c.num_likes, c.times_played, c.clear_check_time, (c.creator.creator_name,
            c.creator.creator_avatar, c.creator.creator_nationality), c.tag1, c.tag2, (c.world_record_holder.name, c.world_record_holder.avatar, c.world_record_holder.nationality),
            c.wr_time, (c.first_player.name, c.first_player.avatar, c.first_player.nationality), c.clear_rate, c.completed, c.total_times, c.oss_image_url))
      })
  }
}

