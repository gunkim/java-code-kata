package io.github.gunkim.io.console

import io.github.gunkim.io.Input

class ConsoleInput : Input {
    override fun read(): String = readLine() ?: throw IllegalArgumentException("계산식은 필수 입력 값입니다.")
}