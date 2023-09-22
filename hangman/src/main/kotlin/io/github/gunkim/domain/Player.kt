package io.github.gunkim.domain

import java.util.*

data class Player(
    val problem: Word,
    val letters: SortedMap<Int, Letter> = sortedMapOf()
) {
    val isCorrect: Boolean
        get() = letters.size == problem.length

    fun hit(letter: Letter): Player {
        val index = problem.indexOf(letter)
        if (index == -1) return this

        val newLetters = letters.apply { this[index] = letter }
        return Player(problem, newLetters)
    }
}