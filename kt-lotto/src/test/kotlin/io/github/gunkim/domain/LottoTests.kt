package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LottoTests {
    @Test
    fun `로또번호가 6개이면 로또가 생성된다`() {
        assertDoesNotThrow { createLotto() }
    }

    @Test
    fun `문자열로 로또를 생성한다`() {
        assertDoesNotThrow { Lotto.from("1,2,3,4,5,6") }
    }

    @ParameterizedTest
    @ValueSource(ints = [5, 7])
    fun `로또번호가 6개가 아닐 경우 로또가 생성되지 않는다`(lottoCnt: Int) {
        assertThrows<IllegalArgumentException> {
            Lotto((1..lottoCnt).map(::LottoNumber))
        }.apply { assertThat(message).isEqualTo("로또 번호는 6개여야 합니다.") }
    }

    @Test
    fun `로또번호가 중복될 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            Lotto((1..6).map { LottoNumber(1) })
        }.apply { assertThat(message).isEqualTo("로또 번호는 중복될 수 없습니다.") }
    }

    @Test
    fun `로또번호가 몇개 맞는지 반환한다`() {
        val lotto1 = createLotto()
        val lotto2 = createLotto()

        val matchCount = lotto1.match(lotto2)
        assertThat(matchCount).isEqualTo(6)
    }

    @Test
    fun `로또에 해당 로또번호가 있을 경우 참을 반환한다`() {
        val lotto = createLotto()

        assertThat(lotto.contains(LottoNumber(1))).isTrue
    }

    @Test
    fun `로또에 해당 로또번호가 없을 경우 거짓을 반환한다`() {
        val lotto = createLotto()

        assertThat(lotto.contains(LottoNumber(7))).isFalse
    }

    private fun createLotto(): Lotto = Lotto((1..6).map(::LottoNumber))
}