package goahead.supermm2

import akka.actor._
import akka.stream.ActorMaterializer
import goahead.supermm2.wiki.actors.WikiUserActor
final class Actors(implicit val ctx: Context, val system: ActorSystem, val mat: ActorMaterializer) {
  //modules go here
  val WikiUserActor =
    system.actorOf(Props(new WikiUserActor(ctx)).withDispatcher(ctx.threads.db.id), "wiki-user-actor")
}
