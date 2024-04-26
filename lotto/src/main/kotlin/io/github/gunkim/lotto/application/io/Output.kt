package io.github.gunkim.lotto.application.io

import io.github.gunkim.lotto.domain.Lotto
import io.github.gunkim.lotto.domain.Rank

interface Output {
    fun buyMoneyInputMessage()
    fun buyResultMessage(lottoCnt: Int)
    fun showLotto(lottos: List<Lotto>)
    fun winningNumbersInputMessage()
    fun bonusNumberInputMessage()
    fun showStatistics(results: Map<Rank, Int>)
}
