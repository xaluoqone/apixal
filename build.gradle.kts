import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.xaluoqone"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val localProperties = Properties()
file("local.properties").inputStream().use { localProperties.load(it) }

val host: String = localProperties.getProperty("host")
val dbName: String = localProperties.getProperty("database_name")
val dbUsername: String = localProperties.getProperty("database_username")
val dbPassword: String = localProperties.getProperty("database_password")

val ymlHostPlaceholder = "{HOST}"
val ymlDbNamePlaceholder = "{DATABASE_NAME}"
val ymlDbUserNamePlaceholder = "{DATABASE_USERNAME}"
val ymlDbPasswordPlaceholder = "{DATABASE_PASSWORD}"

tasks.register<Copy>("replaceYml") {
    from("src/main/resources")
    include("application.yml")
    into("build/resources/main")
    filter { line ->
        line.replace(ymlHostPlaceholder, host)
            .replace(ymlDbNamePlaceholder, dbName)
            .replace(ymlDbUserNamePlaceholder, dbUsername)
            .replace(ymlDbPasswordPlaceholder, dbPassword)
    }
}

tasks.named("processResources") {
    dependsOn("replaceYml")
}