sbtPlugin := true

name := "sbt-sonarrunner-plugin"

organization := "com.aol.sbt"

scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-encoding", "UTF-8"
)

javaVersionPrefix in javaVersionCheck := Some("1.6")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.1" % "provided")

ScriptedPlugin.scriptedSettings

scriptedLaunchOpts := {
  scriptedLaunchOpts.value ++
    Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}

scriptedBufferLog := false

libraryDependencies += "org.sonarsource.scanner.api" % "sonar-scanner-api" % "2.9.0.887"

version := "git describe --tags --dirty --always".!!.stripPrefix("v").trim

publishMavenStyle := false

bintrayOrganization := Some("aol")

bintrayPackageLabels := Seq("sbt", "sonar", "sbt-native-packager")

bintrayRepository := "scala"

licenses +=("MIT", url("http://opensource.org/licenses/MIT"))

resourceGenerators in Compile <+= (resourceManaged in Compile, name, version) map { (dir, n, v) =>
  val file = dir / "latest-version.properties"
  val contents = "name=%s\nversion=%s".format(n, v)
  IO.write(file, contents)
  Seq(file)
}