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

    val coroutinesVersion = "1.8.0-RC2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")

    val mybatisVersion = "3.0.3"
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:$mybatisVersion")

    val hutoolVersion = "5.8.25"
    implementation("cn.hutool:hutool-core:$hutoolVersion")

    val okioVersion = "3.7.0"
    implementation("com.squareup.okio:okio:$okioVersion")
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

tasks.register<Copy>("replaceYml") {
    from("src/main/resources")
    include("application.yml")
    into("build/resources/main")
    filter { line ->
        var newLine = line
        localProperties.forEach { entry ->
            newLine = newLine.replace("{${entry.key}}", entry.value as String)
        }
        newLine
    }
}

tasks.named("classes") {
    dependsOn("replaceYml")
}