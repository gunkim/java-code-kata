package io.github.gunkim.domain.car

import io.github.gunkim.domain.car.vo.Name
import io.github.gunkim.domain.car.vo.Position
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("자동차들은")
class CarsTests : StringSpec({
    "움직인다" {
        val sut = Cars(
            listOf(
                Car(Name("gunny")),
                Car(Name("앵미")),
                Car(Name("망주")),
            ),
        )

        val runCars = sut.run { go { true } }

        runCars shouldBe Cars(
            listOf(
                Car(Name("gunny"), Position(1)),
                Car(Name("앵미"), Position(1)),
                Car(Name("망주"), Position(1)),
            ),
        )
    }
    "자동차가 1대 미만일 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { Cars(listOf()) }
            .apply { message shouldBe "자동차가 최소 1대는 존재해야 합니다." }
    }
})
