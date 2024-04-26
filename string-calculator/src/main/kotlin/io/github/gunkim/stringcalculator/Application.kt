package io.github.gunkim.stringcalculator

import io.github.gunkim.stringcalculator.application.Calculator
import io.github.gunkim.stringcalculator.application.io.console.ConsoleInput
import io.github.gunkim.stringcalculator.application.io.console.ConsoleOutput

fun main() = Calculator(
    ConsoleInput(),
    ConsoleOutput()
).run()
