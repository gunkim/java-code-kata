package io.github.gunkim.lotto.domain

data class WinningLotto(
    private val lotto: Lotto,
    private val bonusNumber: LottoNumber
) {
    init {
        require(!lotto.contains(bonusNumber)) {
            "당첨 번호와 보너스 번호는 중복될 수 없습니다."
        }
    }

    fun matches(lotto: Lotto): Pair<Int, Boolean> =
        lotto.match(this.lotto) to lotto.contains(bonusNumber)
}
