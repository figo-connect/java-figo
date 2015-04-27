scalaVersion := "2.11.5"

version := "1.2.2"

name := "figo"

projectDependencies += "junit" % "junit" % "4.11" % "test"

projectDependencies += "com.google.code.gson" % "gson" % "2.2.2"

projectDependencies += "commons-codec" % "commons-codec" % "1.7"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
