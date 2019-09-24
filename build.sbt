
name := """bank-test-service"""
organization := "com.fyle.test"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.8"

libraryDependencies += guice


val dataDependencies = Seq("com.fasterxml.jackson.core" % "jackson-core" % "2.9.4",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.11")

val lombok = "org.projectlombok" % "lombok" % "1.16.18"

val libDependencies = dataDependencies ++ Seq(
  "com.typesafe.akka" %% "akka-http-jackson" % "10.1.1",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-http-jackson" % "10.0.11",
  "com.google.guava" % "guava" % "16.0.1",
  "org.apache.commons" % "commons-lang3" % "3.0",
  guice,
  lombok,
  "org.apache.commons" % "commons-io" % "1.3.2",
  "org.apache.kafka" % "kafka-clients" % "2.2.0",
  "io.ebean" % "ebean" % "11.39.1",
  "org.json" % "json" % "20180813",
  "com.google.code.gson" % "gson" %"2.8.5"
)


lazy val igniteDependencies = Seq(  "org.apache.ignite" % "ignite-core" % "2.7.0",
  "org.apache.ignite" % "ignite-spring" % "2.7.0",
  "org.apache.ignite" % "ignite-indexing" % "2.7.0"
)

lazy val postgresDependencies = Seq("org.postgresql" % "postgresql" % "42.2.4")

lazy val commonSettings = Seq(
  organization := "com.jio.platform",
  libraryDependencies ++= libDependencies ++ dataDependencies ++ postgresDependencies ++ Seq(
    "org.apache.commons" % "commons-lang3" % "3.0"
  )
)



lazy val `bank-test-api` = (project in file("bank-test-api"))
  .settings(
    commonSettings
  )


lazy val `bank-test-impl` = (project in file("bank-test-impl"))
  .settings(
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources",
    libraryDependencies ++= libDependencies ++ igniteDependencies ++ postgresDependencies,
    //AOP
    aspectjLintProperties in Aspectj += "invalidAbsoluteTypeName = ignore",

    aspectjInputs in Aspectj += (aspectjCompiledClasses in Aspectj).value,

    products in Compile := (products in Aspectj).value,

    products in Runtime := (products in Compile).value,

    //playEbeanModels in Compile := Seq("com.jio.platform.orders.entities.*")
  ).dependsOn(`bank-test-api`)
  .enablePlugins(SbtAspectj, PlayEbean)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .dependsOn(`bank-test-api`,`bank-test-impl`)