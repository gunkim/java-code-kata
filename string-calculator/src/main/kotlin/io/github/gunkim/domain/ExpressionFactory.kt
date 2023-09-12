package io.github.gunkim.domain

class ExpressionFactory(
    private val expression: String
) {
    init {
        require(EXPRESSION_PATTERN.matches(expression)) { "유효하지 않은 계산식입니다." }
    }

    fun make() = Expression(
        OPERATOR_PATTERN.split(expression).map(String::toInt),
        NUMBER_PATTERN.split(expression).drop(1).dropLast(1).map(Operator.Companion::get)
    )

    companion object {
        private val EXPRESSION_PATTERN = Regex("^(\\d+ [+\\-*/] )+(\\d)\$")
        private val NUMBER_PATTERN = Regex(" ?(\\d)+ ?")
        private val OPERATOR_PATTERN = Regex(" ?[+\\-*/] ?")
    }
}
