package io.github.gunkim.application

import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Output
import io.github.gunkim.domain.*

class LottoSystem(
    private val input: Input,
    private val output: Output,
    numberFactory: NumberFactory,
) {
    private val lottoMachine = LottoMachine(numberFactory)

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
}