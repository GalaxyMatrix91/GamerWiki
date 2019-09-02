package goahead.supermm2

import java.io.File
import java.time.LocalDate

import com.typesafe.config.Config
import goahead.libs.model.Magic._
import goahead.libs.model.RunningMode

import scala.jdk.CollectionConverters._

final class Settings(val underlying: Config) {
  // 根配置
  val Root = underlying.getConfig("goahead")
  // 版本信息
  val VersionName = Root.getString("version")
  // Build 版本号
  val VersionCode = Root.getString("build")
  // 语言
  val Langs = Root.getStringList("langs").asScala.toList
  // 全版本号
  val Version = s"$VersionName.$VersionCode"
  // 运行模式
  val Mode = RunningMode.valueOf(Root.getString("mode"))
  // CPU 核心数
  val CpuCount = Runtime.getRuntime.availableProcessors()
  // WebSite
  val WebSite = Root.getString("website")
  // WebRoot
  val WebRoot = Root.getString("webroot")
  // 静态网页
  val StaticWebSite = Root.getString("static-website")

  /** 在 OSS 上约束：
   *  static 是公开资源，任何人都可以访问
   *  private 是私有数据，需要授权才能访问
   *  internal 是内部数据，需要授权才能访问
   *  所有数据都使用 SHA 模式保存
   */
  val StaticFileUrl = StaticWebSite + "/static"
  // 静态文件 URL，有权限限制
  val PrivateFileUrl = StaticWebSite + "/private"
  // 静态目录
  val StaticHome = new File(Root.getString("static")).getCanonicalFile
  // 私有目录
  val PrivateHome = new File(Root.getString("private")).getCanonicalFile
  // 临时目录, 按天获取
  def TempHome: File = {
    val parent = new File(PrivateHome, "temp")
    val day = LocalDate.now().simple
    new File(parent, day).getCanonicalFile
  }
  // 地址
  val HttpAddress = Root.getString("http.address")
  // 端口号
  val HttpPort = Root.getInt("http.port")
  // cookie config
  val CookieConfig = Root.getConfig("http.cookie")
  // aliyun oss config
  val OssConfig = Root.getConfig("oss")
}

