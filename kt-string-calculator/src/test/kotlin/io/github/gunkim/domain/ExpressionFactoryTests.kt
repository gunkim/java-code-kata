package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

@DisplayName("계산식 공장 테스트")
class ExpressionFactoryTests {
    @ParameterizedTest
    @ValueSource(
        strings = [
            "+ 1 + 2 + 3",
            "1 + 2 +",
            "1 2 + 1",
            "1 + # + 2"
        ]
    )
    fun `유효하지 않은 계산식일 경우 예외가 발생한다`(expression: String) {
        assertThrows<IllegalArgumentException>("유효하지 않은 계산식입니다.")
        { ExpressionFactory(expression) }
    }

    @ParameterizedTest
    @MethodSource("getProvider")
    fun `계산식을 반환한다`(expression: String, expectedValue: Int) {
        val factory = ExpressionFactory(expression)
        val expression = factory.make()

        assertThat(expression.execute()).isEqualTo(expectedValue)
    }

    companion object {
        @JvmStatic
        fun getProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("3 + 2 + 5", 10),
                Arguments.of("5 + 2 - 2 * 10 / 2", 25),
                Arguments.of("5 - 2", 3),
                Arguments.of("4 / 2", 2),
                Arguments.of("5 * 3", 15)
            )
        }
    }
}