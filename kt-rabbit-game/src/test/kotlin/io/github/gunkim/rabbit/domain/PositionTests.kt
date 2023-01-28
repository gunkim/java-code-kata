package io.github.gunkim.rabbit.domain

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec

@DisplayName("위치는")
class PositionTests : StringSpec({
    "음수가 될 경우 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> { Position(-1) }
    }
    "0 ~ 69 사이의 값만 허용된다." {
        shouldNotThrow<IllegalArgumentException> {
            Position(0)
            Position(69)
        }
    }
    "70 이상의 값이 들어올 경우 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> { Position(70) }
    }
    "음수 값이 들어올 경우 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> { Position(-1) }
    }
})
