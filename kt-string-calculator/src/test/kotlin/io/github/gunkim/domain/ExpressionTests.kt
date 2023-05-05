package io.github.gunkim.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("계산식은")
class ExpressionTests : StringSpec({
    "인스턴스가 생성된다" {
        shouldNotThrowAny { Expression(listOf(2, 1), listOf(Operator.PLUS)) }
    }
    "피연산자가 2개 이하일 경우 인스턴스 생성에 실패한다" {
        shouldThrow<IllegalArgumentException> { Expression(listOf(1), listOf(Operator.PLUS)) }
            .apply { message shouldBe "피연산자는 최소 2개 이상이어야 합니다." }
    }
    "연산자가 1개 이하일 경우 인스턴스 생성에 실패한다" {
        shouldThrow<IllegalArgumentException> { Expression(listOf(1, 2), listOf()) }
            .apply { message shouldBe "연산자는 최소 1개 이상이어야 합니다." }
    }
    "피연산자가 연산자보다 1만큼 크지 않을 경우 인스턴스 생성에 실패한다" {
        shouldThrow<IllegalArgumentException> {
            Expression(listOf(1, 2), listOf(Operator.DIVIDE, Operator.MULTPLY))
        }.apply { message shouldBe "연산자는 피연산자보다 1개 적어야 합니다." }
    }
    "연산 결과를 반환한다" {
        val expression = Expression(listOf(1, 2, 3), listOf(Operator.PLUS, Operator.MULTPLY))
        val result = expression.execute()

        result shouldBe 9
    }
})
