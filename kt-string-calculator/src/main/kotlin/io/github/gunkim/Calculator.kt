package io.github.gunkim

import io.github.gunkim.domain.ExpressionFactory
import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Output

class Calculator(
    private val input: Input,
    private val output: Output,
) {
    fun run() {
        output.inputMessage()
        val expression = ExpressionFactory(input.read()).make()
        output.resultMessage(expression.execute())
    }
}