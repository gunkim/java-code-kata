package io.github.gunkim.domain

import java.util.SortedMap

data class Player(
    val problem: Word,
    val letters: SortedMap<Int, Letter> = sortedMapOf(),
) {
    fun hit(letter: Letter): Player {
        val index = problem.letters.indexOf(letter)

        if (index == -1) return this

        val copy = letters.toSortedMap()
            .also { it[index] = letter }

        return Player(problem, copy)
    }

    fun isComplete() = letters.size == problem.length
}
