plugins {
  kotlin("jvm") version "2.1.21"
  id("com.squareup.wire") version "5.4.0"
  id("application")
}

repositories {
    mavenCentral()
}

val applicationMainClass = "com.squareup.exemplar.ExemplarServiceKt"
application {
  mainClass.set(applicationMainClass)
}

dependencies {
  compileOnly(libs.findbugsJsr305)
  implementation(libs.guava)
  implementation(libs.guice)
  implementation(libs.jakartaInject)
  implementation(libs.loggingApi)
  implementation(libs.kotlinXHtml)
  implementation(libs.logbackClassic)
  implementation(libs.moshiCore)
  implementation(libs.slf4jApi)
  implementation(libs.okHttp)
  implementation(libs.okio)
  implementation(libs.wispDeployment)
  implementation(libs.wispLease)
  implementation(libs.miskLogging)
  implementation(libs.wispRateLimiting)
  implementation(libs.wispToken)
  implementation(libs.misk)
  implementation(libs.miskActions)
  implementation(libs.miskAdmin)
  implementation(libs.miskApi)
  implementation(libs.miskAuditClient)
  implementation(libs.miskConfig)
  implementation(libs.miskClustering)
  implementation(libs.miskCore)
  implementation(libs.miskCron)
  implementation(libs.miskInject)
  implementation(libs.miskJdbc)
  implementation(libs.miskHotwire)
  implementation(libs.miskLeaseMysql)
  implementation(libs.miskPrometheus)
  implementation(libs.miskService)
  implementation(libs.miskTailwind)
  implementation(libs.miskTokens)
  implementation(libs.miskTesting)

  testImplementation(libs.assertj)
  testImplementation(libs.awsDynamodb)
  testImplementation(libs.docker)
  testImplementation(libs.dockerApi)
  testImplementation(libs.dockerCore)
  testImplementation(libs.dockerTransportHttpClient)
  testImplementation(libs.jedis)
  testImplementation(libs.junitApi)
  testRuntimeOnly(libs.junitEngine)
  testRuntimeOnly(libs.junitLauncher)
  testImplementation(libs.micrometerCore)
  testRuntimeOnly(libs.mysql)
  testImplementation(libs.okHttpMockWebServer)
  testImplementation(libs.miskHibernate)
  testImplementation(libs.miskJdbc)
  testImplementation(libs.miskRateLimitingBucket4jDynamodbV1)
  testImplementation(libs.miskRateLimitingBucket4jMysql)
  testImplementation(libs.miskRateLimitingBucket4jRedis)
  testImplementation(libs.miskRedis)
  testImplementation(libs.miskTesting)
  testImplementation(libs.miskVitess)
  testImplementation(libs.tempest2TestingDocker)
  testImplementation(libs.tempest2TestingJvm)
  testImplementation(libs.wispLoggingTesting)

  testImplementation(testFixtures(libs.miskAwsDynamodb))
  testImplementation(testFixtures(libs.miskJdbc))
  testImplementation(testFixtures(libs.miskRedis))
  testImplementation(testFixtures(libs.miskVitess))
}

tasks.jar {
  manifest {
    attributes("Main-Class" to applicationMainClass)
  }
  archiveClassifier.set("unshaded")
}

wire {
  // Necessary to support Grpc Reflection so proto files are available in the runtime classpath, otherwise can remove
  protoLibrary = true

  // Generate service interfaces also.
  kotlin {
    includes = listOf("com.squareup.exemplar.protos.HelloWebService")
    rpcRole = "server"
    rpcCallStyle = "blocking"
    singleMethodServices = true
  }

  kotlin {
    rpcRole = "client"
    javaInterop = true
  }
}

tasks.test {
  useJUnitPlatform()
}
