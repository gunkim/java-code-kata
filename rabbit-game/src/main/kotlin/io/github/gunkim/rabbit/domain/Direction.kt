package io.github.gunkim.rabbit.domain

enum class Direction(
    val value: String
) {
    LEFT("L"),
    RIGHT("R")
    ;

    companion object {
        fun random() = entries.toTypedArray().random()

        fun ofValue(value: String) = entries.first { it.value == value }
    }
}
