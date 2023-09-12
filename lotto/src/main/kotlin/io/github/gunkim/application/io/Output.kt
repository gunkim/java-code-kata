package io.github.gunkim.application.io

import io.github.gunkim.domain.Lotto
import io.github.gunkim.domain.Rank

interface Output {
    fun buyMoneyInputMessage()
    fun buyResultMessage(lottoCnt: Int)
    fun showLotto(lottos: List<Lotto>)
    fun winningNumbersInputMessage()
    fun bonusNumberInputMessage()
    fun showStatistics(results: Map<Rank, Int>)
}