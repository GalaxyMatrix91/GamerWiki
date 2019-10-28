package goahead.supermm2.wiki.models

/*
            "maker_id": "asd-asd-123",
            "maker_name": "Fucker",
            "maker_points": 1110,
            "maker_avatar": "www.google.com",
            "maker_nationality": "Japan",
            "maker_profile_image": "https://smm2.oss-cn-hangzhou.aliyuncs.com/maker/maker_profile_detail/Screen%20Shot%202019-09-01%20at%2015.24.30.png",
            "ec_easy_high_score": 0,
            "ec_normal_high_score": 0,
            "ec_expert_high_score": 0,
            "ec_super_expert_high_score": 0,
            "mv_wins": 0,
            "mv_plays": 0,
            "mco_clears": 0,
            "mco_plays": 0,
            "ph_courses_played": 0,
            "ph_courses_cleared": 0,
            "ph_attempts": 0,
            "ph_lives_lost": 0
 */
final case class Maker(
                      maker_id: String,
                      maker_name: Option[String],
                      maker_point: Option[Long],
                      maker_avatar: Option[String],
                      maker_nationality: Option[String],
                      maker_profile_image: Option[String],
                      ec_easy_high_score: Option[Long],
                      ec_normal_high_score: Option[Long],
                      ec_expert_high_score: Option[Long],
                      ec_super_expert_high_score: Option[Long],
                      mv_wins: Option[Long],
                      mv_plays: Option[Long],
                      mco_clears: Option[Long],
                      mco_plays: Option[Long],
                      ph_courses_played: Option[Long],
                      ph_courses_cleared: Option[Long],
                      ph_attempts: Option[Long],
                      ph_lives_lost: Option[Long],
                      versus_rating_score: Option[Long],
                      oss_image_url: Option[String],
                      oss_image_detail_url: Option[String]
)

final case class Makers(makers: Seq[Maker])