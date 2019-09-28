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
                      maker_name: String,
                      maker_point: Long,
                      maker_avatar: String,
                      maker_nationality: String,
                      maker_profile_image: String,
                      ec_easy_high_score: Long,
                      ec_normal_high_score: Long,
                      ec_expert_high_score: Long,
                      ec_super_expert_high_score: Long,
                      mv_wins: Long,
                      mv_plays: Long,
                      mco_clears: Long,
                      mco_plays: Long,
                      ph_courses_played: Long,
                      ph_courses_cleared: Long,
                      ph_attempts: Long,
                      ph_lives_lost: Long,
                      versus_rating_score: Long,
                      oss_maker_image: Option[String],
                      oss_maker_detail_image: Option[String]
)

final case class Makers(makers: Seq[Maker])