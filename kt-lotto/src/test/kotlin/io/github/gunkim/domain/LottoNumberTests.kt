package io.github.gunkim.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("로또 번호는")
class LottoNumberTests : StringSpec({
    "1 ~ 45 사이를 벗어난 번호로 생성을 시도할 경우 예외가 발생한다" {
        listOf(0, 46).forEach {
            shouldThrow<IllegalArgumentException> {
                LottoNumber(it)
            }.apply { message shouldBe "'${it}'는 로또번호 범위 1 ~ 45 사이를 벗어납니다." }
        }
    }
})
