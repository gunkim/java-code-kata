import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

val jvmVersion: String by project
val groupName: String by project
val projectVersion: String by project

plugins {
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
    apply(plugin = "kotlin")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = jvmVersion
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
    }
}
