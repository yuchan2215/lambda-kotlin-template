import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("kapt") version "1.7.20"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    implementation("com.amazonaws:aws-java-sdk-lambda:1.12.342")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation(platform("software.amazon.awssdk:bom:2.18.20"))
    implementation("software.amazon.awssdk:dynamodb")
    implementation("software.pando.crypto:salty-coffee:1.1.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    shadowJar {
        minimize()
        mergeServiceFiles()
    }
}