package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class WinningLottoTests {
    @Test
    fun `인스턴스가 생성된다`() {
        assertDoesNotThrow { WinningLotto(createLotto(), LottoNumber(7)) }
    }

    @Test
    fun `당첨번호와 보너스볼이 중복될 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { WinningLotto(createLotto(), LottoNumber(6)) }
            .apply { assertThat(message).isEqualTo("당첨 번호와 보너스 번호는 중복될 수 없습니다.") }
    }

    private fun createLotto(): Lotto = Lotto((1..6).map(::LottoNumber))
}