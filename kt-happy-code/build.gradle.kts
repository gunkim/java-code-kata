plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "io.github.gunkim"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
