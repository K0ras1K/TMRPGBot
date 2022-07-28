import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.jessecorbett:diskord-bot:2.1.4")
    implementation("org.ktorm:ktorm-core:3.5.0")
    implementation(kotlin("stdlib"))
    implementation("mysql:mysql-connector-java:8.0.15")
    testImplementation("junit", "junit", "4.12")
    implementation("com.squareup.moshi:moshi:1.11.0")
    implementation("io.ktor:ktor-server-core:1.6.8")
    implementation("io.ktor:ktor-server-netty:1.6.8")
}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("ru.k0ras1k.tmbot.MainKt")
}