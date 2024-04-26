package io.github.gunkim.lotto.domain

import io.github.gunkim.lotto.domain.Lotto
import io.github.gunkim.lotto.domain.LottoNumber
import io.github.gunkim.lotto.domain.WinningLotto
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows

private fun createLotto(): Lotto = Lotto((1..6).map(::LottoNumber))

@DisplayName("당첨 로또 티켓은")
class WinningLottoTests : StringSpec({
    "당첨 번호와 보너스 볼이 중복될 경우 예외가 발생한다" {
        assertThrows<IllegalArgumentException> { WinningLotto(createLotto(), LottoNumber(6)) }
            .apply { message shouldBe "당첨 번호와 보너스 번호는 중복될 수 없습니다." }
    }
})
