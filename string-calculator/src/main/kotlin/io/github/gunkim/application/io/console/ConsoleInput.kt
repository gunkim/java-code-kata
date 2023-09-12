package io.github.gunkim.application.io.console

import io.github.gunkim.application.io.Input

class ConsoleInput : Input {
    override fun read(): String = readLine() ?: throw IllegalArgumentException("계산식은 필수 입력 값입니다.")
}
