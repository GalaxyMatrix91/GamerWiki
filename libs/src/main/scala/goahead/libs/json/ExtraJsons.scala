package goahead.libs.json

import io.circe.syntax._
import io.circe._
import goahead.libs.model._
// 异常处理(这里的异常对象必须是国际化后的)
final class GoAheadExceptionFormat extends Encoder[GoAheadException] with Decoder[GoAheadException] {

  override def apply(q: GoAheadException): Json = {
    Json.obj("error_code" -> q.errorCode.asJson, "message" -> q.message.asJson, "extra_message" -> q.extra.asJson)
  }

  override def apply(c: HCursor): Decoder.Result[GoAheadException] = {
    for {
      errorCode <- c.downField("error_code").as[Int]
      message <- c.downField("message").as[String]
      extra <- c.downField("extra_message").as[Option[String]]
    } yield GoAheadException(errorCode, message, extra)
  }
}

// 分页
final class PagesFormat extends Encoder[Pages] {
  override def apply(obj: Pages) = {
    Json.obj(
      "total" -> obj.total.asJson,
      "pageSize" -> obj.pageSize.asJson,
      "max" -> obj.max.asJson,
      "current" -> obj.current.asJson,
      "previous" -> obj.previous.asJson,
      "next" -> obj.next.asJson,
      "showing" -> obj.showing.asJson)
  }
}
