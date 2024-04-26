package io.github.gunkim.stringcalculator.application

import io.github.gunkim.stringcalculator.application.io.Input
import io.github.gunkim.stringcalculator.application.io.Output
import io.github.gunkim.stringcalculator.domain.ExpressionFactory

class Calculator(
    private val input: Input,
    private val output: Output
) {
    fun run() {
        output.inputMessage()
        val expression = ExpressionFactory(input.read()).make()
        output.resultMessage(expression.execute())
    }
}
