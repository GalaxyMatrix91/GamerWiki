package goahead.libs.http

import akka.http.scaladsl.server.Rejection

final case class GoAheadRejection(ex: Throwable) extends Rejection
