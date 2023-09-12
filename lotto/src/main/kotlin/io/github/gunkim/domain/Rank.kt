package io.github.gunkim.domain

enum class Rank(
    val reward: Int,
    val matchCnt: Int,
) {
    FIRST(2_000_000_000, 6),
    SECOND(30_000_000, 5),
    THIRD(1_500_000, 5),
    FOURTH(50_000, 4),
    FIFTH(5_000, 3),
    MISS(0, 0);

    operator fun component1(): Int = reward

    operator fun component2(): Int = matchCnt

    companion object {
        fun ranking(hitCnt: Int, isBonusHit: Boolean): Rank = when (hitCnt) {
            6 -> FIRST
            5 -> if (isBonusHit) SECOND else THIRD
            4 -> FOURTH
            3 -> FIFTH
            else -> MISS
        }
    }
}