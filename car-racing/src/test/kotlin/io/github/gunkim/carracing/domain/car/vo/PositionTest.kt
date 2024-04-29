package io.github.gunkim.carracing.domain.car.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("자동차 위치는")
class PositionTest : StringSpec({
    "음수일 경우 인스턴스 생성이 되지 않는다" {
        shouldThrow<IllegalArgumentException> { Position(-1) }
            .apply { message shouldBe "위치는 음수일 수 없습니다." }
    }
})
