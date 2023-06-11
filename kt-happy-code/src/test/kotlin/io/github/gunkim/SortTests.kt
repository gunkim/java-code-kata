package io.github.gunkim

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private val example = listOf(
    intArrayOf(5, 9, 1, 6, 7, 8, 2, 4, 3),
    intArrayOf(3, 1, 2, 9, 8, 4, 5, 7, 6),
    intArrayOf(4, 2, 8, 3, 1, 7, 6, 5, 9),
    intArrayOf(7, 6, 5, 9, 8, 3, 1, 4, 2),
    intArrayOf(2, 1, 9, 4, 3, 7, 8, 5, 6)
)

@DisplayName("정렬 알고리즘 테스트")
class SortTests : StringSpec({
    "퀵 정렬" {
        example.forEach {
            quickSort(it) shouldBe intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        }
    }
    "짝수가 먼저 오도록 정렬한다" {
        example.forEach {
            evenNumberFirstSort(it) shouldBe intArrayOf(2, 4, 6, 8, 1, 3, 5, 7, 9)
        }
    }
    "네덜란드 국기에 맞게 정렬한다" {
        example.forEach {
            dutchFlagPartition(
                listOf(
                    Color.WHITE,
                    Color.RED,
                    Color.RED,
                    Color.WHITE,
                    Color.WHITE,
                    Color.BLUE,
                    Color.BLUE,
                    Color.RED,
                    Color.BLUE
                )
            ) shouldBe listOf(
                Color.RED,
                Color.RED,
                Color.RED,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.BLUE,
                Color.BLUE,
                Color.BLUE
            )
        }
    }
})
