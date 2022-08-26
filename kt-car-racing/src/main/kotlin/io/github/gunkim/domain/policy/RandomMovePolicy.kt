package io.github.gunkim.domain.policy

import kotlin.random.Random

class RandomMovePolicy(
    private val random: Random = Random(10)
) : MovePolicy {
    override fun isMove(): Boolean = random.nextInt() >= 4
}