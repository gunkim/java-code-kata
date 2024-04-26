package io.github.gunkim.hangman.application

import io.github.gunkim.hangman.domain.Word
import io.github.gunkim.hangman.domain.Words

fun interface FindWord {
    fun find(): Word

    class DefaultFindWord(
        private val words: Words
    ) : FindWord {
        override fun find() = words.findRandom()
    }
}
