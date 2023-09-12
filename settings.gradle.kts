pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include("lotto")
include("happy-code")
include("rabbit-game")
include("hangman")
include("car-racing")
include("string-calculator")

rootProject.name = "code-kata"
