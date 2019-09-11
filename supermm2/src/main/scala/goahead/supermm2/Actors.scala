package goahead.supermm2

import akka.actor._
import akka.stream.ActorMaterializer
import goahead.supermm2.wiki.WikiActor

final class Actors(implicit val ctx: Context, val system: ActorSystem, val mat: ActorMaterializer) {
  //modules go here
  val WikiActor =
    system.actorOf(Props(new WikiActor(ctx)).withDispatcher(ctx.threads.db.id), "wiki-actor")
}
