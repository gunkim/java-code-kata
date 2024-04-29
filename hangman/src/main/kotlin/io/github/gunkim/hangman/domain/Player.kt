package io.github.gunkim.hangman.domain

typealias LettersMap = Map<Int, Letter>

data class Player(
    val problem: Word,
    val letters: LettersMap = sortedMapOf()
) {
    val isCorrect: Boolean
        get() = letters.size == problem.length

    fun hit(letter: Letter): Player {
        val index = problem.indexOf(letter)
        return if (index != -1) {
            copy(letters = letters + (index to letter))
        } else {
            this
        }
    }
}
