package io.github.gunkim.lotto.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize

@DisplayName("로또 발권기는")
class LottoMachineTests : StringSpec({
    "받은 돈 만큼 로또 티켓을 발행한다" {
        var i = 1
        val lottos = LottoMachine { i++ }.buy(2_500)

        lottos shouldHaveSize 2
    }
    "티켓 한장 가격보다 적은 돈을 받을 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { LottoMachine { 1 }.buy(500) }
    }
})
