package io.github.gunkim.stringcalculator.application.io.console

import io.github.gunkim.stringcalculator.application.io.Input

class ConsoleInput : Input {
    override fun read(): String = readlnOrNull()
        ?: throw IllegalArgumentException("계산식은 필수 입력 값입니다.")
}
