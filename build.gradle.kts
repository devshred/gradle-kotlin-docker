import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import com.palantir.gradle.docker.DockerExtension
import com.palantir.gradle.docker.DockerRunExtension

plugins {
    id("org.springframework.boot") version "2.6.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    id("com.palantir.docker") version "0.31.0"
    id("com.palantir.docker-run") version "0.31.0"
}

group = "org.devshred"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

val bootJar: BootJar by tasks
val projectName = project.name
val dockerImageName = "devshred/$projectName"

configure<DockerExtension> {
    name = dockerImageName
    tag("latest", "latest")

    buildArgs(mapOf("JAR_FILE" to bootJar.archiveFileName.get()))
    setDockerfile(file("src/main/docker/Dockerfile"))
    files(bootJar.archiveFile)
    pull(true)
}

configure<DockerRunExtension> {
    name = projectName
    image = dockerImageName
    daemonize = false
    clean = true
    ports("8080:8080")
}