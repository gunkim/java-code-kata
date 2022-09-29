package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Position
import io.github.gunkim.domain.car.vo.Name
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CarsTests {
    @Test
    fun `차들을 움직인다`() {
        val sut = Cars(
            listOf(
                Car(Name("gunny")),
                Car(Name("앵미")),
                Car(Name("망주"))
            )
        ).run { go { true } }

        val expectedPosition = Position(1)
        assertThat(sut.list).extracting("forward").contains(expectedPosition, expectedPosition, expectedPosition)
    }

    @Test
    fun `자동차가 1대 미만일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Cars(listOf()) }
            .apply { assertThat(message).isEqualTo("자동차가 최소 1대는 존재해야 합니다.") }
    }
}