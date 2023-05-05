package io.github.gunkim.domain

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("로또 등수는")
class RankTests : StringSpec({
    "맞은 번호 갯수에 맞는 등수를 반환한다" {
        listOf(
            Triple(2, false, Rank.MISS),
            Triple(3, false, Rank.FIFTH),
            Triple(4, false, Rank.FOURTH),
            Triple(5, false, Rank.THIRD),
            Triple(5, true, Rank.SECOND),
            Triple(6, false, Rank.FIRST),
        ).forEach { (hitCnt, isBonusHit, expectedRank) ->
            val rank = Rank.ranking(hitCnt, isBonusHit)

            rank shouldBe expectedRank
        }
    }
})
