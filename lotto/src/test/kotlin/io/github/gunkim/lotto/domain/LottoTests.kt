package io.github.gunkim.lotto.domain

import io.github.gunkim.lotto.domain.Lotto
import io.github.gunkim.lotto.domain.LottoNumber
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private fun createLotto() = Lotto((1..6).map(::LottoNumber))

@DisplayName("로또 티켓은")
class LottoTests : StringSpec({
    "문자열로 생성된다" {
        shouldNotThrowAny { Lotto.from("1,2,3,4,5,6") }
    }
    "번호가 6개가 아닐 경우 예외가 발생된다" {
        listOf(5, 7).forEach {
            shouldThrow<IllegalArgumentException> {
                Lotto((1..it).map(::LottoNumber))
            }.apply { message shouldBe "로또 번호는 6개여야 합니다." }
        }
    }
    "번호가 중복될 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> {
            Lotto((1..6).map { LottoNumber(1) })
        }.apply { message shouldBe "로또 번호는 중복될 수 없습니다." }
    }
    "다른 로또 티켓과 비교해서 맞은 갯수를 반환한다" {
        val lotto1 = createLotto()
        val lotto2 = createLotto()

        val matchCount = lotto1.match(lotto2)

        matchCount shouldBe 6
    }
    "번호를 받아 번호가 있을 경우 참을 반환한다" {
        val lotto = createLotto()

        lotto.contains(LottoNumber(1)) shouldBe true
    }
    "번호를 받아 번호가 없을 경우 거짓을 반환한다" {
        val lotto = createLotto()

        lotto.contains(LottoNumber(7)) shouldBe false
    }
})
