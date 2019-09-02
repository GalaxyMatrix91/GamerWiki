package goahead.supermm2

import akka.actor.ActorSystem
import com.typesafe.config.Config
import goahead.libs.i18n.Langs
import goahead.libs.slick.SlickDatabase

import scala.util.Try
final case class DBSchema(supermm2: String = "smm2_wiki")

//System thread pool
final class Threads(system: ActorSystem) {
  val db = system.dispatchers.lookup("goahead.threads.db")
  val client = system.dispatchers.lookup("goahead.threads.client")
}

//上下文信息
sealed trait Context extends AutoCloseable with wiki.WikiContext {
  // 数据库 schema
  val schema: DBSchema
  // 配置项
  val settings: Settings
  // 线程池
  val threads: Threads
  // 多语言
  val langs: Langs
  // 运行数据库
  val DB: SlickDatabase

  // 关闭资源
  def close(): Unit = {
    DB.close()
  }
}

object Context {

  def from(sysConfig: Config)(implicit actorSystem: ActorSystem): Try[Context] = Try {
    val localSettings = new Settings(sysConfig)
    val localSchema = DBSchema()
    val pool = new Threads(actorSystem)
    val loader = getClass.getClassLoader
    val codes = localSettings.Langs
    val db = SlickDatabase.postgres("goahead.db", sysConfig)(pool.db)
    // 返回实例
    new Context {
      final val settings = localSettings
      final val threads = pool
      final val langs = Langs.from(loader, codes)
      final val DB = db
      final val schema = localSchema
    }
  }
}
