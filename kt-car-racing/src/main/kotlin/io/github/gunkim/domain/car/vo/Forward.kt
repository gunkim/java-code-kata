package io.github.gunkim.domain.car.vo

data class Forward(val value: Int = 0) {
    init {
        require(value >= 0) { "위치는 음수일 수 없습니다." }
    }

    operator fun plus(number: Int) = Forward(value + number)
}