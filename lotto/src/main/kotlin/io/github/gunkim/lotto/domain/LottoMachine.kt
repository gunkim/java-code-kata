package io.github.gunkim.lotto.domain

import io.github.gunkim.lotto.domain.Lotto.Companion.NUMBER_SIZE

data class LottoMachine(
    private val factory: CreateNumberPolicy
) {
    fun buy(money: Int): List<Lotto> {
        require(money >= PRICE) { "한장도 살 수 없는 금액입니다." }

        return generateSequence { createLottoNumbers() }
            .take(money / PRICE)
            .map(::Lotto)
            .toList()
    }

    private fun createLottoNumbers() = generateSequence(factory::create)
        .distinct()
        .take(NUMBER_SIZE)
        .map(::LottoNumber)
        .toList()

    companion object {
        private const val PRICE = 1_000
    }
}
