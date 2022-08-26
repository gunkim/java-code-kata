package io.github.gunkim.application.io.console

import io.github.gunkim.application.io.Input

class ConsoleInput: Input {
    override fun input(): String = readLine() ?: throw IllegalArgumentException("무조건 입력해주셔야 댐")
    override fun inputInt(): Int = readln().toInt()
}