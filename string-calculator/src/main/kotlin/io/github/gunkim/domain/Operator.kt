package io.github.gunkim.domain

enum class Operator(
    private val operator: String,
    private val func: (Int, Int) -> Int
) {
    PLUS("+", { num1, num2 -> num1 + num2 }),
    MINUS("-", { num1, num2 -> num1 - num2 }),
    MULTPLY("*", { num1, num2 -> num1 * num2 }),
    DIVIDE("/", { num1, num2 -> num1 / num2 });

    fun execute(num1: Int, num2: Int) = func(num1, num2)

    companion object {
        fun get(operator: String) = values()
            .firstOrNull { it.operator == operator }
            ?: throw IllegalArgumentException("연산자를 찾을 수 없습니다.")
    }
}
