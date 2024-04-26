package io.github.gunkim.stringcalculator.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("계산식 팩토리는")
class ExpressionFactoryTests : StringSpec({
    "유효하지 않은 계산식일 경우 예외가 발생한다" {
        arrayOf(
            "+ 1 + 2 + 3",
            "1 + 2 +",
            "1 2 + 1",
            "1 + # + 2"
        ).forEach {
            shouldThrow<IllegalArgumentException> {
                ExpressionFactory(it)
            }.apply { message shouldBe "유효하지 않은 계산식입니다." }
        }
    }
    "계산식을 반환한다" {
        listOf(
            "3 + 2 + 5" to 10,
            "5 + 2 - 2 * 10 / 2" to 25,
            "5 - 2" to 3,
            "4 / 2" to 2,
            "5 * 3" to 15
        ).forEach { (expressionKey, expectedValue) ->
            val factory = ExpressionFactory(expressionKey)
            val expression = factory.make()

            expression.execute() shouldBe expectedValue
        }
    }
})
