package com.goahead

import akka.actor.ActorSystem
import com.goahead.actors.{ MainActor, RoutesActor }

object Boot extends App{
  implicit val system = ActorSystem("SUPERMM2WIKI")

  system.actorOf(MainActor.props(RoutesActor.props _))

}
