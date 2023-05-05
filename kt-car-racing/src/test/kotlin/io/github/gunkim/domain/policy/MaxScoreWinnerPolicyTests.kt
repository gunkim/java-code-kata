package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car
import io.github.gunkim.domain.car.vo.Name
import io.github.gunkim.domain.car.vo.Position
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly

private val sut = MaxScoreWinnerPolicy()

@DisplayName("최고 점수 우승 정책은")
class MaxScoreWinnerPolicyTests : StringSpec({
    "우승자를 반환한다" {
        val participants = listOf(
            Car(Name("거니"), Position(1)),
            Car(Name("앵미"), Position(2)),
            Car(Name("망주"), Position(3)),
            Car(Name("주니"), Position(3)),
        )

        val winners = sut.winner(participants)

        winners shouldContainExactly listOf(
            Car(Name("망주"), Position(3)),
            Car(Name("주니"), Position(3)),
        )
    }
})
