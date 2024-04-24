package io.github.gunkim.domain

import java.util.random.RandomGenerator

class CreateRandomNumberPolicy : CreateNumberPolicy {
    override fun create(): Int = randomGenerator.nextInt(LottoNumber.MAX_NUMBER) + 1

    companion object {
        private val randomGenerator = RandomGenerator.getDefault()
    }
}
