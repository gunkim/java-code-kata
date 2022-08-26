package io.github.gunkim.domain.car

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CarRaceTrackTests {
    @Test
    fun `게임을 진행한다`() {
        val sut = createCars(
            listOf(
                Car("gunny"),
                Car("앵미"),
                Car("망주")
            )
        )

        sut.round(3)

        assertThat(sut.cars).extracting("forward").contains(3, 3, 3)
    }

    @Test
    fun `레이싱이 시작되기 전에 우승자를 찾을 경우 예외가 발생한다`() {
        val sut = createCars(listOf(Car("테스터")))

        assertThrows<IllegalArgumentException> { sut.winners }
            .apply { assertThat(message).isEqualTo("레이싱이 시작되지 않았습니다.") }
    }

    @Test
    fun `자동차가 1대 미만일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { createCars(listOf()) }
            .apply { assertThat(message).isEqualTo("자동차가 최소 1대는 존재해야 합니다.") }
    }

    private fun createCars(cars: List<Car>): CarRaceTrack = CarRaceTrack(cars, { true }, { listOf() })
}