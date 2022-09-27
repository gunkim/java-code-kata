package io.github.gunkim.domain.car.vo

data class Name(val value: String) {
    init {
        require(value.isNotBlank()) { "이름은 공백일 수 없습니다." }
    }
}