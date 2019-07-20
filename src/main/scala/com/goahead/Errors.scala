package com.goahead

case class Errors(
                 errorCode: Int,
                 msg: String,
                 extra: Option[String] = None,
                 exception: Option[Throwable] = None
                 ) extends Exception(msg, exception.orNull) {
  // 添加异常信息
  def extra(extra: String): Errors = {
    copy(extra = Some(extra))
  }

  // 添加异常类
  def extra(extra: Throwable): Errors = {
    copy(exception = Some(extra), extra = Some(extra.getMessage))
  }

  override def toString = super.toString + extra.fold("")(" " + _)
}
