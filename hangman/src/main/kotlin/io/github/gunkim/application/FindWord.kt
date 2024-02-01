package io.github.gunkim.application

import io.github.gunkim.domain.Word
import io.github.gunkim.domain.Words

fun interface FindWord {
    fun find(): Word

    class DefaultFindWord(
        private val words: Words
    ) : FindWord {
        override fun find() = words.findRandom()
    }
}
