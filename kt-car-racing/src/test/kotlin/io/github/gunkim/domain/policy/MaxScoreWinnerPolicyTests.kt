package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MaxScoreWinnerPolicyTests {
    private val sut = MaxScoreWinnerPolicy()

    @Test
    fun `우승자를 반환한다`() {
        val winners = sut.winner(
            listOf(
                Car("거니", 1),
                Car("앵미", 2),
                Car("망주", 3),
                Car("주니", 3),
            )
        )
        assertThat(winners).extracting("name").contains("망주", "주니")
    }
}