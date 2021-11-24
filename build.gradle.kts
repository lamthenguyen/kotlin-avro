import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.davidmc24.gradle.plugin.avro.GenerateAvroJavaTask

plugins {
    id("org.springframework.boot") version "2.1.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    id("com.github.davidmc24.gradle.plugin.avro") version ("1.3.0")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

project.ext.set("avroVersion", "1.11.0")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.avro:avro:${project.ext.get("avroVersion")}")
    implementation("org.apache.avro:avro-tools:${project.ext.get("avroVersion")}")

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.2.0")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params", version = "5.2.0")
    testRuntime(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation(group = "io.rest-assured", name = "rest-assured", version = "3.3.0")
    testImplementation(kotlin("test-junit5"))
}

tasks.withType<KotlinCompile> {
    dependsOn("generateAvro")
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

val avroBuildDir = "$buildDir/generated-resources"
val relativePathToAvroGeneratedCode = "src/main/kotlin"
val avroOutputDir = "$avroBuildDir/$relativePathToAvroGeneratedCode"
kotlin.sourceSets["main"].kotlin.srcDir("$avroOutputDir")

task<GenerateAvroJavaTask>("generateAvro") {
    source("src/main/resources/static/avro")
    setOutputDir(file("$avroOutputDir"))
}

avro {
    stringType.set("CharSequence")
    fieldVisibility.set("private")
    customConversion(org.apache.avro.Conversions.UUIDConversion::class.java)
}
