package io.github.gunkim.rabbit.application

import io.github.gunkim.rabbit.domain.Direction
import io.github.gunkim.rabbit.domain.Position

class MoveInput(
    private val value: String,
) {
    operator fun component1() = Position(value.replace(DIRECTION_REGEX, "").toInt())
    operator fun component2() = Direction.ofValue(value.replace(POSITION_REGEX, ""))

    companion object {
        private val POSITION_REGEX = "[0-9]".toRegex()
        private val DIRECTION_REGEX = "[LR]".toRegex()
    }
}
