package io.github.gunkim.tetris.domain

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@DisplayName("Piece는")
class PieceTests : StringSpec({
    "회전한다" {
        val piece = Piece(Color.RED, Shape.I)
        val rotatedPiece = piece.rotate()

        rotatedPiece.rotate shouldBe 1
    }
    "4회 이상 회전하면 제자리로 돌아온다" {
        val piece = Piece(Color.RED, Shape.I, 3)
        val rotatedPiece = piece.rotate()

        rotatedPiece.rotate shouldBe 0
    }
})
