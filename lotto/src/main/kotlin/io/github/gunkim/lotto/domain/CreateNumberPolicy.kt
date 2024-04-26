package io.github.gunkim.lotto.domain

fun interface CreateNumberPolicy {
    fun create(): Int
}
