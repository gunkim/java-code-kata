package io.github.gunkim.lotto.domain

data class Lotto(val numbers: List<LottoNumber>) {
    init {
        require(numbers.size == NUMBER_SIZE) { "로또 번호는 ${NUMBER_SIZE}개여야 합니다." }
        require(numbers.distinct().size == NUMBER_SIZE) { "로또 번호는 중복될 수 없습니다." }
    }

    fun match(lotto: Lotto) = numbers
        .map(lotto::contains)
        .count { it }

    fun contains(lottoNumber: LottoNumber) = numbers.contains(lottoNumber)

    companion object {
        const val NUMBER_SIZE = 6

        fun from(numbers: String) = Lotto(
            numbers.replace(" ", "")
                .split(",")
                .map(String::toInt)
                .map(::LottoNumber)
        )
    }
}
