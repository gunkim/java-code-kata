package io.github.gunkim.domain.car

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CarRaceTrackTests {
    @Test
    fun `레이싱이 시작되기 전에 우승자를 찾을 경우 예외가 발생한다`() {
        val sut = createCars(listOf(Car("테스터")))

        assertThrows<IllegalArgumentException> { sut.winners }
            .apply { assertThat(message).isEqualTo("레이싱이 시작되지 않았습니다.") }
    }

    private fun createCars(cars: List<Car>): CarRaceTrack = CarRaceTrack(Cars(cars), { true }, { listOf() })
}