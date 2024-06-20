pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        val kotlinVersion: String by settings
        val ktlintVersion: String by settings
        kotlin("jvm") version kotlinVersion
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "code-kata"

include("lotto")
include("rabbit-game")
include("hangman")
include("car-racing")
include("string-calculator")
include("data-systems")
include("blackjack")
include("banking")
