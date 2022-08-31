package io.github.gunkim.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RankTests {
    @ParameterizedTest
    @MethodSource("rankingProvider")
    fun 정답_갯수에_맞는_등수를_반환한다(hitCnt: Int, isBonusHit: Boolean, expectedRank: Rank) {
        val rank = Rank.ranking(hitCnt, isBonusHit)
        assertThat(rank).isEqualTo(expectedRank)
    }

    companion object {
        @JvmStatic
        fun rankingProvider(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(2, false, Rank.MISS),
                Arguments.of(3, false, Rank.FIFTH),
                Arguments.of(4, false, Rank.FOURTH),
                Arguments.of(5, false, Rank.THIRD),
                Arguments.of(5, true, Rank.SECOND),
                Arguments.of(6, false, Rank.FIRST),
            )
        }
    }
}