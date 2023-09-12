package io.github.gunkim.rabbit.domain

data class Position(
    val value: Int,
) {
    init {
        require(value in 0..69) { "0 ~ 69 사이의 위치만 허용됩니다." }
    }

    operator fun plus(position: Position) = Position(this.value + position.value)

    operator fun minus(position: Position) = Position(this.value - position.value)

    companion object {
        fun random() = Position((0..69).random())
    }
}
