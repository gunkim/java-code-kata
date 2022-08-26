package io.github.gunkim.application.io

import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput

enum class Mode(
    val input: Input,
    val output: Output,
) {
    CONSOLE(ConsoleInput(), ConsoleOutput());
}