package io.github.gunkim.domain.car.vo

data class Forward(val forward: Int = 0) {
    init {
        require(forward >= 0) {
            "위치는 음수일 수 없습니다."
        }
    }
}