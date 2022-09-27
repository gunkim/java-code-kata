package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Forward
import io.github.gunkim.domain.car.vo.Name
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CarTests {
    @Test
    fun `전진한다`() {
        val car = Car(Name("안녕"))
            .run(Car::go)

        assertThat(car.forward).isEqualTo(Forward(1))
    }
    @Test
    fun `자동차 이름이 공백일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Car(Name(" ")) }
            .apply { assertThat(message).isEqualTo("이름은 공백일 수 없습니다.") }
    }
    @Test
    fun `자동차의 위치가 음수일 경우 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Car(Name("벤츠"), Forward(-1)) }
            .apply { assertThat(message).isEqualTo("위치는 음수일 수 없습니다.") }
    }
}