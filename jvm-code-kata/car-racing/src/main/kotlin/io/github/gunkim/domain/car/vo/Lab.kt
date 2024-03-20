package io.github.gunkim.domain.car.vo

data class Lab(
    val value: Int
) {
    init {
        require(value > 0) { "게임 횟수는 1번 이상이어야 합니다." }
    }
}
