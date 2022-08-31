package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class LottoMachineTests {
    @Test
    fun `인스턴스가 생성된다`() {
        var i = 1
        assertDoesNotThrow {
            LottoMachine {
                i++
            }
        }
    }

    @Test
    fun `로또 티켓을 돈만큼 발행한다`() {
        var i = 1
        val lottos = LottoMachine {
            i++
        }.buy(2_500)

        assertThat(lottos).hasSize(2)
    }

    @Test
    fun `티켓 한장 가격보다 적은 돈일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> {
            LottoMachine { 1 }.buy(500)
        }
    }
}