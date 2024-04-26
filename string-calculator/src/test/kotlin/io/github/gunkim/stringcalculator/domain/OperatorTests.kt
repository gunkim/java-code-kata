package io.github.gunkim.stringcalculator.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("연산자는")
class OperatorTests : StringSpec({
    "연산자를 찾아서 반환한다" {
        listOf(
            "+" to Operator.PLUS,
            "-" to Operator.MINUS,
            "*" to Operator.MULTIPLY,
            "/" to Operator.DIVIDE
        ).forEach { (operator, expectedValue) ->
            Operator.get(operator) shouldBe expectedValue
        }
    }
    "연산자를 찾을 수 없을 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { Operator.get("test") }
            .apply { message shouldBe "연산자를 찾을 수 없습니다." }
    }
    "연산을 수행 후 결과를 반환한다" {
        listOf(
            Operator.PLUS to 3,
            Operator.MINUS to -1,
            Operator.MULTIPLY to 2,
            Operator.DIVIDE to 0
        ).forEach { (operator, expectedValue) ->
            val result = operator.execute(1, 2)
            result shouldBe expectedValue
        }
    }
})
