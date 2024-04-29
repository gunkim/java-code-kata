package io.github.gunkim.hangman.domain

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("플레이어는")
class PlayerTests : StringSpec({
    "생성된다." {
        val player = Player(Word.from("한글"))
        player.problem.value shouldBe "한글"
    }
    "문제를 맞출 수 있다." {
        val player = Player(Word.from("한글"))
            .hit(Letter("한"))
            .hit(Letter("글"))

        player.letters.size shouldBe 2
        player.letters[0] shouldBe Letter("한")
        player.letters[1] shouldBe Letter("글")
    }
    "문제를 맞추지 못할 경우 같은 객체가 반환된다." {
        val player = Player(Word.from("한글"))
        val hit = player.hit(Letter("돈"))

        hit shouldBe player
    }
})
