plugins {
    id("org.openjfx.javafxplugin") version "0.1.0"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.openjfx:javafx-controls:21.0.2")
    implementation("org.openjfx:javafx-fxml:21.0.2")
}

javafx {
    version = "21.0.2"
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.withType<JavaExec> {
    val javafxLibsPath = "/path/to/javafx-sdk/lib"

    jvmArgs = listOf("--module-path", javafxLibsPath, "--add-modules", "javafx.controls,javafx.fxml")
}