package io.github.gunkim.hangman.data

import io.github.gunkim.hangman.domain.Word
import io.github.gunkim.hangman.domain.Words

class FileReadWords(
    private val fileName: String
) : Words {
    private val words: List<Word> by lazy {
        bufferedReader()
            .readLines()
            .map(Word.Companion::from)
    }

    override fun findRandom(): Word = words.random()

    private fun bufferedReader() = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(fileName)
        ?.reader()
        ?.buffered()
        ?: throw IllegalStateException("파일을 읽을 수 없습니다. (파일명: $fileName)")
}
