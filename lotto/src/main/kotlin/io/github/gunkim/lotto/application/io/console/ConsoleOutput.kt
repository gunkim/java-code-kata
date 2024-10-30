package io.github.gunkim.lotto.application.io.console

import io.github.gunkim.lotto.application.io.Output
import io.github.gunkim.lotto.domain.Lotto
import io.github.gunkim.lotto.domain.LottoNumber
import io.github.gunkim.lotto.domain.Rank

class ConsoleOutput : Output {
    override fun buyMoneyInputMessage() {
        println("구입금액을 입력해주세요.")
    }

    override fun buyResultMessage(lottoCnt: Int) {
        println("${lottoCnt}개를 구매했습니다.")
    }

    override fun showLotto(lottos: List<Lotto>) {
        lottos.forEach { lotto ->
            println(lotto.numbers.map(LottoNumber::value))
        }
    }

    override fun winningNumbersInputMessage() {
        println("지난 주 당첨번호를 입력해 주세요.")
    }

    override fun bonusNumberInputMessage() {
        println("보너스 볼을 입력해 주세요.")
    }

    override fun showStatistics(results: Map<Rank, Int>) {
        println("당첨 통계")
        println("---------")
        Rank.entries.forEach { showRank(it, results) }
    }

    private fun showRank(rank: Rank, results: Map<Rank, Int>) {
        val resultCnt = results[rank] ?: return
        val reward = rank.reward

        println("${rank.number}등 (${formatMoney(reward)}) - ${resultCnt}개")
    }

    private fun formatMoney(money: Int) = money.toString().replace(MONEY_PATTERN, "$1,")

    companion object {
        private val MONEY_PATTERN = Regex("(\\d)(?=(?:\\d{3})+(?!\\d))")
    }
}
