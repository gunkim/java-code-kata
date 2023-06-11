package io.github.gunkim

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("정렬 알고리즘 테스트")
class SortTests : StringSpec({
    "퀵 정렬" {
        listOf(
            intArrayOf(5, 9, 1, 6, 7, 8, 2, 4, 3),
            intArrayOf(3, 1, 2, 9, 8, 4, 5, 7, 6),
            intArrayOf(4, 2, 8, 3, 1, 7, 6, 5, 9),
            intArrayOf(7, 6, 5, 9, 8, 3, 1, 4, 2),
            intArrayOf(2, 1, 9, 4, 3, 7, 8, 5, 6)
        ).forEach {
            quickSort(it) shouldBe intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        }
    }
})
