package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

@DisplayName("계산식 테스트")
class ExpressionTests {

    @Test
    fun `인스턴스가 생성된다`() {
        assertDoesNotThrow { Expression(listOf(2, 1), listOf(Operator.PLUS)) }
    }

    @Test
    fun `숫자가 2개 이하일 경우 인스턴스 생성에 실패한다`() {
        assertThrows<IllegalArgumentException>("계산할 값은 최소 2개 이하여야 합니다.")
        { Expression(listOf(1), listOf(Operator.PLUS)) }
    }

    @Test
    fun `연산자가 1개 이하일 경우 인스턴스 생성에 실패한다`() {
        assertThrows<IllegalArgumentException>("연산자는 최소 1개 이하여야 합니다.")
        { Expression(listOf(1, 2), listOf()) }
    }

    @Test
    fun `숫자가 연산자보다 1만큼 크지 않을 경우 인스턴스 생성에 실패한다`() {
        assertThrows<IllegalArgumentException>("연산자는 계산할 값보다 1개 적어야 합니다.")
        { Expression(listOf(1, 2), listOf(Operator.DIVIDE, Operator.MULTPLY)) }
    }

    @Test
    fun `연산 결과를 반환한다`() {
        val expression = Expression(listOf(1, 2, 3), listOf(Operator.PLUS, Operator.MULTPLY))
        val result = expression.execute()

        assertThat(result).isEqualTo(9)
    }
}