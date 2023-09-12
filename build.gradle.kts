import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
}

allprojects {
    group = "io.github.gunkim"
    version = "2023.09.12"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "17"
        }
        test {
            useJUnitPlatform()
        }
    }

    dependencies {
        testImplementation("io.kotest:kotest-runner-junit5:5.6.1")
    }
}
