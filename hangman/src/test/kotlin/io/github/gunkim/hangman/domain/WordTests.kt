package io.github.gunkim.hangman.domain

import io.github.gunkim.hangman.domain.Letter
import io.github.gunkim.hangman.domain.Word
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("단어는")
class WordTests : StringSpec({
    "생성된다." {
        val value = listOf(
            Letter("한"),
            Letter("글")
        )

        val word = Word(value)
        word.letters shouldBe value
    }
    "한 글자 이상이 아닐 경우 예외가 발생한다." {
        shouldThrow<IllegalArgumentException> { Word(listOf()) }
            .message shouldBe "단어는 한 글자 이상이어야 합니다."
    }
    "8자를 넘어갈 경우 예외가 발생한다." {
        val value = listOf(
            Letter("한"),
            Letter("글"),
            Letter("입"),
            Letter("니"),
            Letter("다"),
            Letter("만"),
            Letter("불"),
            Letter("만"),
            Letter("있")
        )
        shouldThrow<IllegalArgumentException> { Word(value) }
            .message shouldBe "단어는 8자를 넘을 수 없습니다. (현재: $value)"
    }
    "내용을 문자열로 반환한다." {
        val value = listOf(
            Letter("한"),
            Letter("글")
        )
        val word = Word(value)
        word.value shouldBe "한글"
    }
    "길이를 반환한다." {
        val value = listOf(
            Letter("한"),
            Letter("글")
        )
        val word = Word(value)
        word.length shouldBe 2
    }
})
