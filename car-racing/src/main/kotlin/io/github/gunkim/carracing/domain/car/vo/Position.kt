package io.github.gunkim.carracing.domain.car.vo

data class Position(val value: Int = 0) {
    init {
        require(value >= 0) { "위치는 음수일 수 없습니다." }
    }

    operator fun plus(number: Int) = Position(value + number)

    companion object {
        val ZERO = Position(0)
    }
}
