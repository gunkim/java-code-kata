package io.github.gunkim.application

import io.github.gunkim.Calculator
import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput

fun main() = Calculator(
    ConsoleInput(),
    ConsoleOutput()
).run()
