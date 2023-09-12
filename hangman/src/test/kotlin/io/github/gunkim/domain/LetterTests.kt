package io.github.gunkim.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("글자는")
class LetterTests : StringSpec({
    "생성된다." {
        val letter = Letter("한")
        letter.value shouldBe "한"
    }
    "한 글자가 아닐 경우 예외가 발생한다." {
        val value = "한글"
        shouldThrow<IllegalArgumentException> { Letter(value) }
            .message shouldBe "한 글자만 허용됩니다. (현재: $value)"
    }
    "한글이 아닐 경우 예외가 발생한다." {
        listOf(
            "K",
            "1",
            "@"
        ).forEach {
            shouldThrow<IllegalArgumentException> { Letter(it) }
                .message shouldBe "한글만 허용됩니다. (현재: $it)"
        }
    }
})
