package io.github.gunkim

import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput

fun main() = Calculator(
    ConsoleInput(),
    ConsoleOutput()
).run()
