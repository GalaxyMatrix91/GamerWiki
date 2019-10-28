package goahead.supermm2
package wiki
package routes

import goahead.libs.json.Jsons
import goahead.supermm2.wiki.models._
import akka.http.scaladsl.server.Route
import goahead.libs.model.{ShaFile, Token}
import org.apache.commons.io._
import akka.pattern._
import goahead.supermm2.wiki.actors.OssActor
import io.circe.Json
import goahead.supermm2.jmodel._
import scala.concurrent.Future

final case class FileRoute(webRoot: String, actors: Actors) extends Supermm2Route(actors) {
  import actors._


  override def otherRoute: Route = (post & path(webRoot / "upload-private")) {
      uploadFiles { f =>
        logger.debug("FormField: {}", f)
        val ext = FilenameUtils.getExtension(f.fileName.get)
        FileUtils.getFile(ctx.settings.TempHome, Token.millis("TEMP").value + "." + ext)
      } { fields =>
        val futures = fields.filter(_.isFile).map { f =>
          WikiActor.ask(OssActor.UploadPrivate(f.target.get)).mapTo[ShaFile].map { r =>
            FileReply(f.field, r.parent, r.sha, r.toUrl)
          }
        }
        Future.sequence(futures).map { r =>
          Json.obj("files" -> Jsons.toJson(r))
        }
      }

  }
}
