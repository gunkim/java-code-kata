package io.github.gunkim.domain

class Expression private constructor(
    private val numbers: ArrayDeque<Int>,
    private val operators: ArrayDeque<Operator>
) {
    constructor(numbers: Collection<Int>, operators: Collection<Operator>) :
        this(ArrayDeque(numbers), ArrayDeque(operators))

    init {
        require(operators.isNotEmpty()) { "연산자는 최소 1개 이상이어야 합니다." }
        require(numbers.size >= 2) { "피연산자는 최소 2개 이상이어야 합니다." }
        require((numbers.size - 1) == operators.size) { "연산자는 피연산자보다 1개 적어야 합니다." }
    }

    val operator: Operator
        get() = operators.removeFirst()

    fun execute(): Int = numbers.reduce(this::execute)

    private fun execute(num1: Int, num2: Int) = operator.execute(num1, num2)
}
