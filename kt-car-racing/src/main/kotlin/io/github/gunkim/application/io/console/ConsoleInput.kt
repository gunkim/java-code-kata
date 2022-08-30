package io.github.gunkim.application.io.console

import io.github.gunkim.application.io.Input

class ConsoleInput : Input {
    override val carname: String
        get() = readLine() ?: throw IllegalArgumentException("무조건 입력해주셔야 댐")
    override val maxRound: Int
        get() = readln().toInt()
}