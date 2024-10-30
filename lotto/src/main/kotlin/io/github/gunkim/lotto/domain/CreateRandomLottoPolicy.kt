package io.github.gunkim.lotto.domain

import java.util.random.RandomGenerator

class CreateRandomLottoPolicy : CreateLottoPolicy {
    override fun createLotto() = Lotto(
        generateSequence(::createLottoNumber)
            .distinct()
            .take(Lotto.NUMBER_SIZE)
            .toList()
    )

    private fun createLottoNumber() = LottoNumber(
        randomGenerator.nextInt(LottoNumber.MAX_NUMBER) + 1
    )

    companion object {
        private val randomGenerator = RandomGenerator.getDefault()
    }
}
