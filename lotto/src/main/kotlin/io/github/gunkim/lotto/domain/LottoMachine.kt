package io.github.gunkim.lotto.domain

data class LottoMachine(
    private val factory: CreateLottoPolicy,
) {
    fun buy(money: Int): List<Lotto> {
        require(money >= PRICE) { "한장도 살 수 없는 금액입니다." }

        return generateSequence(factory::createLotto)
            .take(money / PRICE)
            .toList()
    }

    companion object {
        private const val PRICE = 1_000
    }
}
