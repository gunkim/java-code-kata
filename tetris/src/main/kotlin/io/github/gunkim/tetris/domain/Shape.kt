package io.github.gunkim.tetris.domain

enum class Shape(
    val value: Array<IntArray>
) {
    I(
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0)
        )
    ),
    J(
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 0)
        )
    ),
    L(
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 0),
            intArrayOf(0, 1, 1)
        )
    ),
    O(
        arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 1)
        )
    ),
    S(
        arrayOf(
            intArrayOf(0, 1, 1),
            intArrayOf(1, 1, 0),
            intArrayOf(0, 0, 0)
        )
    ),
    Z(
        arrayOf(
            intArrayOf(1, 1, 0),
            intArrayOf(0, 1, 1),
            intArrayOf(0, 0, 0)
        )
    ),
    T(
        arrayOf(
            intArrayOf(0, 1, 0),
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 0)
        )
    )
}
