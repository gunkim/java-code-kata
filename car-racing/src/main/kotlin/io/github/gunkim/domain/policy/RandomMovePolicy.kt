package io.github.gunkim.domain.policy

import java.util.random.RandomGenerator

class RandomMovePolicy(
    private val random: RandomGenerator = RandomGenerator.getDefault()
) : MovePolicy {
    override fun isMove(): Boolean = random.nextInt(10) >= 4
}
