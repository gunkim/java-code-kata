package io.github.gunkim.hangman.domain

fun interface Words {
    fun findRandom(): Word
}
