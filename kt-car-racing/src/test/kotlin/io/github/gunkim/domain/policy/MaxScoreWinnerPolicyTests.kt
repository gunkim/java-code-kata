package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car
import io.github.gunkim.domain.car.vo.Position
import io.github.gunkim.domain.car.vo.Name
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MaxScoreWinnerPolicyTests {
    private val sut = MaxScoreWinnerPolicy()

    @Test
    fun `우승자를 반환한다`() {
        val winners = sut.winner(
            listOf(
                Car(Name("거니"), Position(1)),
                Car(Name("앵미"), Position(2)),
                Car(Name("망주"), Position(3)),
                Car(Name("주니"), Position(3)),
            )
        )
        assertThat(winners).extracting("name").contains(Name("망주"), Name("주니"))
    }
}