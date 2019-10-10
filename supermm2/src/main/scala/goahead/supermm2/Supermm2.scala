package goahead.supermm2


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import goahead.libs.boot.Boot

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.StdIn

object Supermm2 extends Boot with CorsSupport {

  // 关闭系统
  def shutdown(binding: Future[Http.ServerBinding])(implicit ctx: Context, system: ActorSystem): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    logger.info(s"Terminating(${ctx.settings.Mode})...")
    binding.flatMap(_.unbind()).onComplete { _ =>
      Http().shutdownAllConnectionPools()
      ctx.close()
      system.terminate()
    }
    Await.result(system.whenTerminated, 10.seconds) // 等待关闭
  }

  // 加载系统
  def main(args: Array[String]): Unit = initWith() { config =>
    implicit val system = ActorSystem("supermm2", config)
    implicit val mat = ActorMaterializer()
    implicit val ctx = Context.from(config).get
    val cors = new CorsSupport {}
    val actors = new Actors()
    val route = new Routes(ctx.settings.WebRoot, actors)
    implicit val rejectHandler = route.rejectionHandler
    implicit val exceptionHandler = route.exceptionHandler
    // 启动 server
    val bindingFuture = Http().bindAndHandle(cors.corsHandler(route.route), ctx.settings.HttpAddress, ctx.settings.HttpPort)
    logger.info("Server({}) Started on {}:{}", ctx.settings.Mode, ctx.settings.HttpAddress, ctx.settings.HttpPort)
    if (ctx.settings.Mode.isDev) {
      StdIn.readLine() // 如果是开发环境，就等到输入回车键，再退出
      shutdown(bindingFuture)
    } else {
      scala.sys.addShutdownHook {
        shutdown(bindingFuture)
      }
    }
  }
}

