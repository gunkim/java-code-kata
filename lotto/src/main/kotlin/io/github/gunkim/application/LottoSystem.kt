package io.github.gunkim.application

import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Output
import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput
import io.github.gunkim.domain.CreateRandomNumberPolicy
import io.github.gunkim.domain.Lotto
import io.github.gunkim.domain.LottoMachine
import io.github.gunkim.domain.LottoNumber
import io.github.gunkim.domain.Rank
import io.github.gunkim.domain.WinningLotto

class LottoSystem(
    private val input: Input,
    private val output: Output,
    private val lottoMachine: LottoMachine
) {
    fun run() {
        output.buyMoneyInputMessage()
        val money = input.money

        val lottos: List<Lotto> = lottoMachine.buy(money)
        output.buyResultMessage(lottos.size)
        output.showLotto(lottos)

        output.winningNumbersInputMessage()
        val winning = Lotto.from(input.winningNumbers)

        output.bonusNumberInputMessage()
        val bonusNumber = LottoNumber(input.bonusNumber)

        val winningLotto = WinningLotto(winning, bonusNumber)

        val results: Map<Rank, Int> = lottos
            .map(winningLotto::matches)
            .map { Rank.ranking(it.first, it.second) }
            .groupingBy { it }.eachCount()
        output.showStatistics(results)
    }

    companion object {
        fun default(): LottoSystem = LottoSystem(
            ConsoleInput(),
            ConsoleOutput(),
            LottoMachine(CreateRandomNumberPolicy())
        )
    }
}
