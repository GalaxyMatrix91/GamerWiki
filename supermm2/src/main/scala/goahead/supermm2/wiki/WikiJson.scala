package goahead.supermm2.wiki

import goahead.libs.json._
import goahead.supermm2.wiki.models._
import goahead.supermm2.wiki.actors.WikiUserActor._

trait WikiJson extends JsonTrait {
  implicit val AdminIDJson = Jsons.id(AdminID.apply, AdminID.unapply)
  implicit val RoleIDJson = Jsons.id(RoleID.apply, RoleID.unapply)

  implicit val CreatorJson = Jsons.format[Creator]
  implicit val PlayerJson = Jsons.format[Player]
  implicit val CourseJson = Jsons.format[Course]
  implicit val QueryCourseFormJson = Jsons.format[QueryCourseForm]
  implicit val AddCourseFormJson = Jsons.format[AddCourseForm]
  implicit val UpdateCourseFormJson  = Jsons.format[UpdateCourseForm]
  implicit val LoginFormJson = Jsons.format[LoginForm]
  implicit val MakerJson = Jsons.format[Maker]
  implicit val SignUpJson = Jsons.format[SignUp]
  implicit val AdminJson = Jsons.format[Admin]
}
