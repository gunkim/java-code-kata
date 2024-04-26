package io.github.gunkim.hangman.domain

data class Letter(val value: String) {
    init {
        require(value.isNotBlank() && value.length == MINIMUM_LENGTH) { "한 글자만 허용됩니다. (현재: $value)" }
        require(KOREAN_REGEX.matches(value)) { "한글만 허용됩니다. (현재: $value)" }
    }

    companion object {
        private const val MINIMUM_LENGTH = 1
        private val KOREAN_REGEX = Regex("^[가-힣]*\$")
    }
}
