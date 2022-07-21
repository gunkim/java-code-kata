package io.github.gunkim.io.console

import io.github.gunkim.io.Input

class ConsoleInput : Input {
    override fun read(): String = readLine()!!
}