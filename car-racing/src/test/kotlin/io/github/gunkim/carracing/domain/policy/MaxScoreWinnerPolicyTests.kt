package io.github.gunkim.carracing.domain.policy

import io.github.gunkim.carracing.domain.car.Car
import io.github.gunkim.carracing.domain.car.Cars
import io.github.gunkim.carracing.domain.car.vo.Name
import io.github.gunkim.carracing.domain.car.vo.Position
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private val sut = MaxScoreWinnerPolicy()

@DisplayName("최고 점수 우승 정책은")
class MaxScoreWinnerPolicyTests : StringSpec({
    "우승자를 반환한다" {
        val participants = listOf(
            Car(Name("거니"), Position(1)),
            Car(Name("앵미"), Position(2)),
            Car(Name("망주"), Position(3)),
            Car(Name("주니"), Position(3))
        )

        val winners = sut.winner(participants)

        winners shouldBe Cars(
            listOf(
                Car(Name("망주"), Position(3)),
                Car(Name("주니"), Position(3))
            )
        )
    }
})
