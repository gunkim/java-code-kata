package io.github.gunkim.application.io.console

import io.github.gunkim.application.io.Input

class ConsoleInput : Input {
    override val money
        get() = readln().toInt()
    override val winningNumbers
        get() = readlnOrNull() ?: throw IllegalArgumentException("당첨 번호는 입력되어야 합니다.")
    override val bonusNumber
        get() = readln().toInt()
}
