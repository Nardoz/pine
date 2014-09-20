import AssemblyKeys._

name := "pine-backend"

version := "0.1.0"

scalaVersion := "2.10.4"

// retrieveManaged := true

assemblySettings

// unmanagedJars in Compile <++= baseDirectory map { base =>
//   // val hiveFile = file(System.getenv("HIVE_HOME")) / "lib"
//   // val baseDirectories = (base / "lib") +++ (hiveFile)
//   // val customJars = (baseDirectories ** "*.jar")
//   // // Hive uses an old version of guava that doesn't have what we want.
//   // customJars.classpath.filter(!_.toString.contains("guava"))
// }

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.withSource := true

// resolvers ++= Seq(
//    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
//    "Sonatype Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2/",
//    "Spring Plugin Release" at "http://repo.springsource.org/plugins-release/",
//    "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository",
//    "Maven Repository" at "http://repo1.maven.org/maven2",
//    "Akka Repository" at "http://repo.akka.io/releases/",
//    "Spray Repository" at "http://repo.spray.cc/"
// )

assemblyOption in assembly ~= { _.copy(includeScala = false) }

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
    case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
    case "META-INF/services/org.apache.hadoop.fs.FileSystem" => MergeStrategy.concat
    case "reference.conf" => MergeStrategy.concat
    case _ => MergeStrategy.first
}


val sparkVersion = "1.0.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided" withSources() withJavadoc(),
  "com.datastax.spark" %% "spark-cassandra-connector" % "1.0.0" withSources() withJavadoc()
)
