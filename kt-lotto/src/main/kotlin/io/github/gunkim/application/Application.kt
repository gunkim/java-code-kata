package io.github.gunkim.application

import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput
import io.github.gunkim.domain.RandomNumberFactory

fun main() = LottoSystem(
    ConsoleInput(),
    ConsoleOutput(),
    RandomNumberFactory()
).run()