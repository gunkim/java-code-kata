package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LottoNumberTests {
    @ParameterizedTest
    @ValueSource(ints = [1, 45])
    fun `로또 번호가 생성된다`(number: Int) {
        assertDoesNotThrow { LottoNumber(number) }
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 46])
    fun `0 ~ 45 사이에 값이 아니면 생성에 실패한다`(number: Int) {
        assertThrows<IllegalArgumentException> { LottoNumber(number) }
            .apply { assertThat(message).isEqualTo("'${number}'는 로또번호 범위 1 ~ 45 사이를 벗어납니다.") }
    }
}