package io.github.gunkim.domain

data class LottoNumber(val value: Int) {
    init {
        require(value in 1..45) { "'$value'는 로또번호 범위 1 ~ 45 사이를 벗어납니다." }
    }
}
