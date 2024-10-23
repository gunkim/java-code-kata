import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

val jvmVersion: String by project
val groupName: String by project
val projectVersion: String by project

plugins {
    java
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

allprojects {
    group = groupName
    version = projectVersion

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = jvmVersion
        }
        withType<JavaCompile> {
            sourceCompatibility = jvmVersion
            targetCompatibility = jvmVersion
        }
        test {
            useJUnitPlatform()
        }
    }
    configure<KtlintExtension> {
        debug.set(true)
    }
    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:5.6.1")
        testImplementation(platform("org.junit:junit-bom:5.11.3"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.mockito:mockito-core:5.14.2")
        testImplementation("org.assertj:assertj-core:3.26.3")
    }
}
