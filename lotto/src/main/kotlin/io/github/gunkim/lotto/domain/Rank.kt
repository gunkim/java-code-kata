package io.github.gunkim.lotto.domain

enum class Rank(
    val reward: Int,
    val matchCnt: Int
) {
    FIRST(2_000_000_000, 6),
    SECOND(30_000_000, 5),
    THIRD(1_500_000, 5),
    FOURTH(50_000, 4),
    FIFTH(5_000, 3),
    MISS(0, 0)
    ;

    operator fun component1(): Int = reward

    operator fun component2(): Int = matchCnt

    companion object {
        fun determineRankBasedOnHits(hitCnt: Int, isBonusHit: Boolean): Rank = when (hitCnt) {
            FIRST.matchCnt -> FIRST
            SECOND.matchCnt -> determineRankByBonusHit(isBonusHit)
            FOURTH.matchCnt -> FOURTH
            FIFTH.matchCnt -> FIFTH
            else -> MISS
        }

        private fun determineRankByBonusHit(isBonusHit: Boolean) = if (isBonusHit) SECOND else THIRD
    }
}
