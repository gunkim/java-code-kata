package io.github.gunkim.domain

data class Word(val letters: List<Letter>) {
    init {
        require(letters.isNotEmpty()) { "단어는 한 글자 이상이어야 합니다." }
        require(letters.size <= MAX_LENGTH) { "단어는 ${MAX_LENGTH}자를 넘을 수 없습니다. (현재: $letters)" }
    }

    val length: Int = letters.size
    val value: String
        get() = letters.joinToString(separator = "", transform = Letter::value)

    fun indexOf(letter: Letter): Int = letters.indexOf(letter)

    companion object {
        fun from(word: String) = Word(
            word.split("")
                .filter(String::isNotBlank)
                .map(::Letter)
        )

        private const val MAX_LENGTH = 8
    }
}
