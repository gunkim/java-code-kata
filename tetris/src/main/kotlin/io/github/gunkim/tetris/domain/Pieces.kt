package io.github.gunkim.tetris.domain

class Pieces(
    value: List<Piece>
) {
    val value: List<Piece> = value.toList()

    init {
        require(value.isNotEmpty()) { "Value is empty." }
        require(Shape.entries.size == value.size) { "Shape size is not equal to value size." }
    }

    fun random(): Piece {
        return value.random()
    }

    companion object {
        fun create(): Pieces {
            val colors = ArrayDeque(Color.entries)

            return Shape.entries
                .map { shape -> colors.removeFirst() to shape }
                .map { (color, shape) -> Piece(color, shape) }
                .let(::Pieces)
        }
    }
}
