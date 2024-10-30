package io.github.gunkim.lotto.domain

fun interface CreateLottoPolicy {
    fun createLotto(): Lotto
}
