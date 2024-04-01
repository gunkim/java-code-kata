package io.github.gunkim.domain

enum class Operator(
    private val operator: String,
    private val operation: (Int, Int) -> Int
) {
    PLUS("+", { num1, num2 -> num1 + num2 }),
    MINUS("-", { num1, num2 -> num1 - num2 }),
    MULTIPLY("*", { num1, num2 -> num1 * num2 }),
    DIVIDE("/", { num1, num2 -> num1 / num2 });

    fun execute(num1: Int, num2: Int) = operation(num1, num2)

    companion object {
        fun get(operator: String) =
            entries.firstOrNull { it.operator == operator }
                ?: throw IllegalArgumentException("연산자를 찾을 수 없습니다.")
    }
}
