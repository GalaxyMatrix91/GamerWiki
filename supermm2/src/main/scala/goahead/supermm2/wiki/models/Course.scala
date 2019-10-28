package goahead.supermm2.wiki.models

final case class Courses(courses: Seq[Course])
final case class Course(
                   course_id: String,
                   course_name: Option[String],
                   course_type: Option[String],
                   course_image_url: Option[String],
                   course_detail_url: Option[String],
                   num_likes: Option[Long],
                   times_played: Option[Long],
                   clear_check_time: Option[String],
                   creator: Option[Creator],
                   tag1: Option[String],
                   tag2: Option[String],
                   world_record_holder: Option[Player],
                   wr_time: Option[String],
                   first_player: Option[Player],
                   clear_rate: Option[String],
                   completed: Option[Long],
                   total_times: Option[Long],
                   is_poison: Option[Int],
                   oss_image_url: Option[String],
                   oss_image_detail_url: Option[String]
                 )

final case class Creator(creator_name: Option[String],
                   creator_avatar: Option[String],
                   creator_nationality: Option[String])

final case class Player(name: Option[String], avatar: Option[String], nationality: Option[String])
