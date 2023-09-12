pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include("kt-lotto")
include("kt-happy-code")
include("kt-rabbit-game")
include("kt-hangman")
include("kt-car-racing")
include("kt-string-calculator")

rootProject.name = "code-kata"
