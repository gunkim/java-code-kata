package io.github.gunkim.domain

data class Letter(val value: String) {
    init {
        require(value.isNotBlank() && value.length == 1) { "한 글자만 허용됩니다. (현재: $value)" }
        require(KOREAN_REGEX.matches(value)) { "한글만 허용됩니다. (현재: $value)" }
    }

    companion object {
        private val KOREAN_REGEX = Regex("^[가-힣]*\$")
    }
}
