package goahead.libs.model

// 异常类, errorCode 是错误码，全局唯一的
case class GoAheadException(
    errorCode: Int,
    message: String,
    extra: Option[String] = None,
    exception: Option[Throwable] = None)
    extends Exception(message, exception.orNull) {

  // 添加异常信息
  def extra(extra: String): GoAheadException = {
    copy(extra = Some(extra))
  }

  // 添加异常类
  def extra(extra: Throwable): GoAheadException = {
    copy(exception = Some(extra), extra = Some(extra.getMessage))
  }

  override def toString = super.toString + extra.fold("")(" " + _)
}
