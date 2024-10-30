package io.github.gunkim.lotto.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize

@DisplayName("로또 발권기는")
class LottoMachineTests : StringSpec({
    "받은 돈 만큼 로또 티켓을 발행한다" {
        val buyMoney = 2_500
        val lottos = LottoMachine { Lotto.from("1,2,3,4,5,6") }.buy(buyMoney)

        lottos shouldHaveSize 2
    }
    "티켓 한장 가격보다 적은 돈을 받을 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { LottoMachine { Lotto.from("1,2,3,4,5,6") }.buy(500) }
    }
})
