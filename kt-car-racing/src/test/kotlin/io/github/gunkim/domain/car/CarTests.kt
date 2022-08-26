package io.github.gunkim.domain.car

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CarTests {
    @Test
    fun `전진한다`() {
        val car = Car("안녕")
            .also(Car::go)

        assertThat(car.forward).isEqualTo(1)
    }
    @Test
    fun `자동차 이름이 공백일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Car(" ") }
            .apply { assertThat(message).isEqualTo("자동차 이름은 공백일 수 없습니다.") }
    }
    @Test
    fun `자동차의 위치가 음수일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Car("벤츠", -1) }
            .apply { assertThat(message).isEqualTo("자동차 위치는 음수일 수 없습니다.") }
    }
}