package goahead.supermm2.wiki.tables
/*
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
                      oss_maker_image: Option[String],
                      oss_maker_detail_image: Option[String]
 */
import profile.api._
import goahead.supermm2.DBSchema
import goahead.supermm2.wiki.models._
import scala.concurrent.ExecutionContext

final case class MakerTable(stag: Tag)(implicit schema: DBSchema) extends Table[Maker](stag, Option(schema.supermm2), "makers") {
  def id = column[String]("maker_id", O.PrimaryKey)
  def makerName = column[Option[String]]("maker_name")
  def makerPoint = column[Option[Long]]("maker_point")
  def makerAvatar = column[Option[String]]("maker_avatar")
  def makerNationality = column[Option[String]]("maker_nationality")
  def makerProfileImage = column[Option[String]]("maker_profile_image")
  def ecEasyHighScore = column[Option[Long]]("ec_easy_high_score")
  def ecNormalHighScore = column[Option[Long]]("ec_normal_high_score")
  def ecExpertHighScore = column[Option[Long]]("ec_expert_high_score")
  def ecSuperExpertHighScore = column[Option[Long]]("ec_super_expert_high_score")
  def mvWins = column[Option[Long]]("mv_wins")
  def mvPlays = column[Option[Long]]("mv_plays")
  def mcoClears = column[Option[Long]]("mco_clears")
  def mcoPlays = column[Option[Long]]("mco_plays")
  def phCoursesPlayed = column[Option[Long]]("ph_courses_played")
  def phCoursesCleared = column[Option[Long]]("ph_courses_cleared")
  def phAttempts = column[Option[Long]]("ph_attempts")
  def phLivesLost = column[Option[Long]]("ph_lives_lost")
  def versusRatingScore = column[Option[Long]]("versus_rating_score")
  def ossMakerImage = column[Option[String]]("oss_maker_image")
  def ossMakerDetailImage = column[Option[String]]("oss_maker_detail_image")

  def * = (id, makerName, makerPoint, makerAvatar,
  makerNationality, makerProfileImage, ecEasyHighScore, ecNormalHighScore, ecExpertHighScore,
  ecSuperExpertHighScore, mvWins, mvPlays, mcoClears, mcoPlays, phCoursesPlayed,
  phCoursesCleared, phAttempts, phLivesLost, versusRatingScore, ossMakerImage, ossMakerDetailImage).shaped <>
    (Maker.tupled, Maker.unapply)
}

final class MakerDao(implicit schema: DBSchema, ec: ExecutionContext) {
  val table = TableQuery[MakerTable]
  val auto = table.returning(table.map(_.id)).into { case (p, id) => p.copy(maker_id = id) }

  def add(maker: Maker):DBIO[Maker] = {
    val m = Maker(
      maker_id = maker.maker_id,
      maker_name = maker.maker_name,
      maker_point = maker.maker_point,
      maker_avatar = maker.maker_avatar,
      maker_nationality = maker.maker_nationality,
      maker_profile_image = maker.maker_profile_image,
      ec_easy_high_score = maker.ec_easy_high_score,
      ec_normal_high_score = maker.ec_normal_high_score,
      ec_expert_high_score = maker.ec_expert_high_score,
      ec_super_expert_high_score = maker.ec_super_expert_high_score,
      mv_wins = maker.mv_wins,
      mv_plays = maker.mv_plays,
      mco_clears = maker.mco_clears,
      mco_plays = maker.mco_plays,
      ph_courses_played = maker.ph_courses_played,
      ph_courses_cleared = maker.ph_courses_cleared,
      ph_attempts = maker.ph_attempts,
      ph_lives_lost = maker.ph_lives_lost,
      versus_rating_score = maker.versus_rating_score,
      oss_image_url = maker.oss_image_url,
      oss_image_detail_url = maker.oss_image_detail_url
    )
    auto += m
  }

  def getAllMakersByPointsDesc(): DBIO[Seq[Maker]] =
    table.sortBy(_.makerPoint.desc).result

  def getAllMakersByVersusRatingScoreDesc(): DBIO[Seq[Maker]] =
    table.sortBy(_.versusRatingScore.desc).result

  def findByMakerIds(makers: Seq[String]): DBIO[Seq[Maker]] = {
    table.filter(a => a.id inSetBind makers).result
  }

  def update(maker: Maker): DBIO[Boolean] = {
    table.filter(_.id === maker.maker_id).map(a =>
      (a.makerName, a.makerPoint, a.makerAvatar, a.makerNationality, a.makerProfileImage,
      a.ecEasyHighScore, a.ecNormalHighScore, a.ecExpertHighScore, a.ecSuperExpertHighScore, a.mvWins,
      a.mvPlays, a.mcoClears, a.mcoPlays, a.phCoursesPlayed, a.phCoursesCleared, a.phAttempts, a.phLivesLost,
      a.ossMakerImage, a.ossMakerDetailImage)
    ).update((maker.maker_name, maker.maker_point, maker.maker_avatar, maker.maker_nationality,
    maker.maker_profile_image, maker.ec_easy_high_score, maker.ec_normal_high_score, maker.ec_expert_high_score,
    maker.ec_super_expert_high_score, maker.mv_wins, maker.mv_plays, maker.mco_clears, maker.mco_plays,
      maker.ph_courses_played, maker.ph_courses_cleared, maker.ph_attempts, maker.ph_lives_lost,
      maker.oss_image_url, maker.oss_image_detail_url)).map(_ > 0)
  }

  def insertOrUpdate(maker: Maker): DBIO[Maker] =
    table.insertOrUpdate(maker).map(_ => maker)

  def findByMakerId(makerid: String): DBIO[Option[Maker]] = {
    table.filter(_.id === makerid).result.headOption
  }

}
