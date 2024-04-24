package io.github.gunkim.domain

data class LottoNumber(val value: Int) {
    init {
        require(value in MIN_NUMBER..MAX_NUMBER) { "'$value'는 로또번호 범위 $MIN_NUMBER ~ $MAX_NUMBER 사이를 벗어납니다." }
    }

    companion object {
        const val MIN_NUMBER = 1
        const val MAX_NUMBER = 45
    }
}
