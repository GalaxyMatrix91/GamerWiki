package goahead.supermm2.wiki.tables

import profile.api._
import goahead.supermm2.DBSchema
import goahead.supermm2.wiki.models._
import io.circe.Json

import scala.concurrent.ExecutionContext

final class GameInfoTable(stag: Tag)(implicit schema: DBSchema) extends Table[GameInfo](stag, Option(schema.supermm2), "game_info")
  with BaseTable {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def gamePosterUrl = column[String]("game_poster_url")
  def gameNameZH = column[String]("game_name_zh")
  def gameNameEN = column[String]("game_name_en")
  def gameScreenshotUrls = column[Seq[String]]("game_screenshots_url")
  def gameMetainfo = column[Json]("game_metainfo")
  def gameDescription = column[String]("game_description")

  def * = (id, gamePosterUrl, gameNameZH, gameNameEN, gameScreenshotUrls, gameMetainfo,
  gameDescription, state, remark, created_at, updated_at).shaped <> (GameInfo.tupled, GameInfo.unapply)
}

final class GameInfoDao(implicit schema: DBSchema, ec: ExecutionContext) {
  val table = TableQuery[GameInfoTable]
  val auto = table.returning(table.map(_.id)).into { case (p, id) => p.copy(id = id)}

  def add(gameinfo: GameInfo): DBIO[GameInfo] =
    auto += gameinfo

  def listAllGameInfo: DBIO[Seq[GameInfo]] =
    table.sortBy(_.id).result

  def findById(id: Long): DBIO[Option[GameInfo]] =
    table.filter(_.id === id).result.headOption

  def findByGameNameEN(name: String): DBIO[Option[GameInfo]] =
    table.filter(_.gameNameEN === name).result.headOption

  def inserOrUpdate(gameInfo: GameInfo): DBIO[GameInfo] = {
    table.insertOrUpdate(gameInfo).map(_ => gameInfo)
  }
}