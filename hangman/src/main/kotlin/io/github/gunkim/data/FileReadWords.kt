package io.github.gunkim.data

import io.github.gunkim.domain.Word
import io.github.gunkim.domain.Words

class FileReadWords(
    private val fileName: String
) : Words {
    override fun findRandom(): Word {
        val words = bufferedReader()
            .readLines()
            .map(Word.Companion::from)

        return words.random()
    }

    private fun bufferedReader() = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(fileName)
        ?.reader()
        ?.buffered()
        ?: throw IllegalStateException("파일을 읽을 수 없습니다. (파일명: $fileName)")
}
