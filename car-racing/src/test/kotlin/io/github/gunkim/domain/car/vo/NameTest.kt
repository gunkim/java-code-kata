package io.github.gunkim.domain.car.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("자동차 이름은")
class NameTest : StringSpec({
    "공백으로 인스턴스 생성이 되지 않는다" {
        shouldThrow<IllegalArgumentException> { Name("") }
            .apply { message shouldBe "이름은 공백일 수 없습니다." }
    }
})
