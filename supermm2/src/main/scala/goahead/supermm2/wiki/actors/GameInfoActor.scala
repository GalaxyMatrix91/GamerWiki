package goahead.supermm2
package wiki
package actors

import java.time.ZonedDateTime

import akka.pattern._
import akka.stream.Materializer
import goahead.libs.actor.ActorTrait
import goahead.libs.model.StateID
import goahead.supermm2.wiki.models.{AddGameInfoForm, GameBriefInfo, GameInfo, GameInfos, QueryAllGameInfo, QueryGameInfoForm}

import scala.concurrent.duration._

final class GameInfoActor(ctx: Context)(implicit mat: Materializer) extends ActorTrait {
  import ctx._

  override def preStart(): Unit = {
    context.setReceiveTimeout(1.hours)
  }

  override def postStop(): Unit = {}

  override def receive: Receive = {
    case req: AddGameInfoForm => uploadGameInfo(req).pipeTo(sender())
    case QueryAllGameInfo => listAllGameInfo.pipeTo(sender())
    case req: QueryGameInfoForm => findGameInfoById(req).pipeTo(sender())
  }

  def listAllGameInfo = {
    DB.runt(GameInfoDao.listAllGameInfo).map { gameList =>
      gameList.map(game => GameBriefInfo(game.id, game.game_name_zh, game.game_name_en))
    }.map(a => GameInfos(a))
  }

  def findGameInfoById(req: QueryGameInfoForm) =
    DB.runt(GameInfoDao.findById(req.id))

  def addGameInfo(req: AddGameInfoForm) = {
    val gameinfo = GameInfo(
      id = 0L,
      game_poster_url = req.game_poster_url,
      game_name_zh = req.game_name_zh,
      game_name_en = req.game_name_en,
      game_screenshot_urls = req.game_screenshot_urls,
      game_metainfo = req.game_metainfo,
      game_description = req.game_description,
      state = StateID.Enable,
      remark = req.remark,
      created_at = ZonedDateTime.now,
      updated_at = ZonedDateTime.now
    )
    DB.runt(GameInfoDao.add(gameinfo))
  }

  def uploadGameInfo(req: AddGameInfoForm) = {
    val q = GameInfoDao.findByGameNameEN(req.game_name_en).flatMap {
      case Some(gameInfo) =>
        val record = GameInfo(
          id = gameInfo.id,
          game_poster_url = req.game_poster_url,
          game_name_zh = req.game_name_zh,
          game_name_en = req.game_name_en,
          game_screenshot_urls = req.game_screenshot_urls,
          game_metainfo = req.game_metainfo,
          game_description = req.game_description,
          state = gameInfo.state,
          remark = req.remark,
          created_at = gameInfo.created_at,
          updated_at = ZonedDateTime.now
        )
        GameInfoDao.inserOrUpdate(record)
      case None =>
        val gameInfo = GameInfo(
          id = 0L,
          game_poster_url = req.game_poster_url,
          game_name_zh = req.game_name_zh,
          game_name_en = req.game_name_en,
          game_screenshot_urls = req.game_screenshot_urls,
          game_metainfo = req.game_metainfo,
          game_description = req.game_description,
          state = StateID.Enable,
          remark = req.remark,
          created_at = ZonedDateTime.now,
          updated_at = ZonedDateTime.now
        )
        GameInfoDao.add(gameInfo)
    }
    DB.runt(q)
  }

}
