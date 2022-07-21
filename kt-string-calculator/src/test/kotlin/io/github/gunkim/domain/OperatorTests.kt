package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("연산자 테스트")
class OperatorTests {
    @ParameterizedTest
    @MethodSource("getProvider")
    fun `연산자을 찾아서 반환한다`(operator: String, expectedValue: Operator) {
        assertThat(Operator.get(operator)).isEqualTo(expectedValue)
    }

    @Test
    fun `연산자를 찾을 수 없을 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException>("연산자를 찾을 수 없습니다.")
        { Operator.get("test") }
    }

    @ParameterizedTest
    @MethodSource("executeProvider")
    fun `연산을 실행한다`(operator: Operator, expectedValue: Int) {
        val result = operator.execute(1, 2)
        assertThat(result).isEqualTo(expectedValue)
    }

    companion object {
        @JvmStatic
        fun getProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("+", Operator.PLUS),
                Arguments.of("-", Operator.MINUS),
                Arguments.of("*", Operator.MULTPLY),
                Arguments.of("/", Operator.DIVIDE)
            )
        }

        @JvmStatic
        fun executeProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(Operator.PLUS, 3),
                Arguments.of(Operator.MINUS, -1),
                Arguments.of(Operator.MULTPLY, 2),
                Arguments.of(Operator.DIVIDE, 0)
            )
        }
    }
}