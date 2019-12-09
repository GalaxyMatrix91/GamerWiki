package goahead.supermm2.wiki.models

import java.time.ZonedDateTime

import goahead.libs.model.StateID
import io.circe.Json

/*
GameInfo

id

game_poster_url

game_name
	zh_name 死亡搁浅
	en_name Death Stranding
game_sreenshots_url

game_meta_info
	genre 动作
	platforms PlayStation 4, Miscrosoft Windows
	developers 小岛制作(Kojima Productions)
	publishers 索尼互动娱乐(PS4), 505 Games(Windows)
	directors 小岛秀夫
	producers 小岛秀夫，今泉健一郎
	designers 小岛秀夫
	writers
	artists

game_description

state

remark

created_at

updated_at
 */
final case class GameInfo (
    id: Long,
    game_poster_url: String,
    game_name_zh: String,
    game_name_en: String,
    game_screenshot_urls: Seq[String],
    game_metainfo: Json,
    game_description: String,
    state: StateID,
    remark: Option[String],
    created_at: ZonedDateTime,
    updated_at: ZonedDateTime
)

final case class QueryGameInfoForm(
    id: Long
) extends GameInfoMessage

case object QueryAllGameInfo extends GameInfoMessage

final case class AddGameInfoForm(
      game_poster_url: String,
      game_name_zh: String,
      game_name_en: String,
      game_screenshot_urls: Seq[String],
      game_metainfo: Json,
      game_description: String,
      remark: Option[String] = None
) extends GameInfoMessage

final case class GameInfos(gameInfos: Seq[GameInfo])