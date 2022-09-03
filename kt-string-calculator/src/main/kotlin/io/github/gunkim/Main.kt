package io.github.gunkim

import io.github.gunkim.io.console.ConsoleInput
import io.github.gunkim.io.console.ConsoleOutput

fun main() = Calculator(
    ConsoleInput(),
    ConsoleOutput()
).run()