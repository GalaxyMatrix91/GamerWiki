package goahead.supermm2
package wiki.actors

import java.io.File

import akka.pattern._
import akka.actor.Props
import akka.stream.Materializer
import goahead.libs.client.oss.{OssClient, OssSetting}
import goahead.libs.actor.ActorTrait
import goahead.libs.model.{SHA, ShaFile}
import goahead.supermm2.wiki.models.OssMessage

import scala.concurrent.Future
import scala.util.Try
object OssActor {
  def props(ctx: Context)(implicit mat: Materializer): Try[Props] = Try {
    val conf = ctx.settings.OssConfig
    implicit val ossConfig = OssSetting.from(conf).get
    Props(new OssActor(ctx))
  }

  final case class UploadPrivate(from: File) extends OssMessage

  final case class UploadInternal(name: String, from: File) extends OssMessage

  final case class UploadInternalWithString(name: String, content: String) extends OssMessage

}

final class OssActor(ctx: Context)(implicit ossMeta: OssSetting, mat: Materializer) extends ActorTrait {
  import OssActor._
  import ctx._

  val PrivateHome = "/private"
  val InternalHome = "/internal"
  val ossClient = new OssClient(ossMeta.apiUrl)

  override def receive: Receive = {
    case req: UploadPrivate         => uploadPrivate(req).pipeTo(sender())
  }

  def uploadPrivate(req: UploadPrivate): Future[ShaFile] = {
    log.debug("Upload2SHA: {}", req)
    Future.fromTry(SHA.from(req.from)).flatMap {sha =>
      val ossName = sha.toUrl(PrivateHome)
      ossClient.putFile(req.from, ossName).map(r => ShaFile(ossMeta.toUrl(PrivateHome), sha))
    }
  }

}
