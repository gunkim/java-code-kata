package io.github.gunkim.domain

import java.util.random.RandomGenerator

class CreateRandomNumberPolicy : CreateNumberPolicy {
    override fun create(): Int = randomGenerator.nextInt(MAXIMUM_LOTTO_NUMBER) + 1

    companion object {
        private const val MAXIMUM_LOTTO_NUMBER = 45

        private val randomGenerator = RandomGenerator.getDefault()
    }
}
