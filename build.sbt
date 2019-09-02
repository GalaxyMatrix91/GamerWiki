import NativePackagerHelper._

Common.init("Gamer-wiki")

topLevelDirectory := None

//base library
lazy val libs =
  project.in(file("libs")).settings(Common.init("libs", isLib = true), Libs.libsDeps)

//supermm2
lazy val supermm2 =
  project
      .in(file("supermm2"))
      .settings(
        Common.init("supermm2"),
        makeBatScripts := Seq(),  // 不需要 Windows 启动脚本
        mappings in Universal ++= (baseDirectory.value / "dist" / "conf" * "*" get) map(x => x -> ("conf/" + x.getName)),
        mappings in Universal ++= (baseDirectory.value / "dist" / "bin" * "*" get) map(x => x -> ("bin/" + x.getName))
      )
      .dependsOn(libs).aggregate(libs)
      .enablePlugins(JavaAppPackaging, UniversalPlugin)

enablePlugins(JavaAppPackaging, UniversalPlugin)

mappings in (Compile, packageDoc) := Seq()
mappings in Universal ++= directory("conf")