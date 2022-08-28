package io.github.gunkim.domain.car

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CarsTests {
    @Test
    fun `차들을 움직인다`() {
        val sut = Cars(
            listOf(
                Car("gunny"),
                Car("앵미"),
                Car("망주")
            )
        )

        sut.go { true }

        assertThat(sut.list).extracting("forward").contains(1, 1, 1)
    }

    @Test
    fun `자동차가 1대 미만일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Cars(listOf()) }
            .apply { assertThat(message).isEqualTo("자동차가 최소 1대는 존재해야 합니다.") }
    }
}