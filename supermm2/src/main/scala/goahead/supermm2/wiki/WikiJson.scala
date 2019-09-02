package goahead.supermm2.wiki

import goahead.libs.json._
import goahead.supermm2.wiki.models._

trait WikiJson extends JsonTrait {
  implicit val CreatorJson = Jsons.format[Creator]
  implicit val PlayerJson = Jsons.format[Player]
  implicit val CourseJson = Jsons.format[Course]
}
