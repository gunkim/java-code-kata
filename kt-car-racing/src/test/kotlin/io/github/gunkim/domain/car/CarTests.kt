package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Name
import io.github.gunkim.domain.car.vo.Position
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("자동차는")
class CarTests : StringSpec({
    "전진한다" {
        val car = Car(Name("안녕"))
            .run(Car::go)

        car.position shouldBe Position(1)
    }
    "자동차 이름이 공백일 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { Car(Name(" ")) }
            .apply { message shouldBe "이름은 공백일 수 없습니다." }
    }
    "자동차의 위치가 음수일 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { Car(Name("벤츠"), Position(-1)) }
            .apply { message shouldBe "위치는 음수일 수 없습니다." }
    }
})
