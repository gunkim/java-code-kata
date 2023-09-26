package io.github.gunkim.tetris.domain

data class Piece(
    val color: Color,
    val shape: Shape,
    val rotate: Int = 0
) {
    fun rotate(): Piece {
        return copy(rotate = (rotate + 1) % MAX_ROTATE_COUNT)
    }

    companion object {
        const val MAX_ROTATE_COUNT = 4
    }
}
