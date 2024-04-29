package io.github.gunkim.carracing.domain.car.vo

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("경기장은")
class LabTests : StringSpec({
    "인스턴스가 생성된다" {
        shouldNotThrowAny { Lab(1) }
    }
    "0 이하일 경우 예외가 발생한다" {
        shouldThrow<IllegalArgumentException> { Lab(0) }
            .apply { message shouldBe "게임 횟수는 1번 이상이어야 합니다." }
    }
})
