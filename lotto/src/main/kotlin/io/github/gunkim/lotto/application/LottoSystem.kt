package io.github.gunkim.lotto.application

import io.github.gunkim.lotto.application.io.Input
import io.github.gunkim.lotto.application.io.Output
import io.github.gunkim.lotto.application.io.console.ConsoleInput
import io.github.gunkim.lotto.application.io.console.ConsoleOutput
import io.github.gunkim.lotto.domain.CreateRandomLottoPolicy
import io.github.gunkim.lotto.domain.Lotto
import io.github.gunkim.lotto.domain.LottoMachine
import io.github.gunkim.lotto.domain.LottoNumber
import io.github.gunkim.lotto.domain.Rank
import io.github.gunkim.lotto.domain.WinningLotto

class LottoSystem(
    private val input: Input,
    private val output: Output,
    private val lottoMachine: LottoMachine
) {
    fun run() {
        output.buyMoneyInputMessage()
        val money = input.money

        val purchasedLottos: List<Lotto> = lottoMachine.buy(money)
        output.buyResultMessage(purchasedLottos.size)
        output.showLotto(purchasedLottos)

        output.winningNumbersInputMessage()
        val winning = Lotto.from(input.winningNumbers)

        output.bonusNumberInputMessage()
        val bonusNumber = LottoNumber(input.bonusNumber)

        val winningLotto = WinningLotto(winning, bonusNumber)

        val results: Map<Rank, Int> = purchasedLottos
            .map(winningLotto::matches)
            .map { Rank.determineRankBasedOnHits(it.first, it.second) }
            .groupingBy { it }.eachCount()
        output.showStatistics(results)
    }

    companion object {
        fun default(): LottoSystem = LottoSystem(
            ConsoleInput(),
            ConsoleOutput(),
            LottoMachine(CreateRandomLottoPolicy())
        )
    }
}
