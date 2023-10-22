package io.github.gunkim.rabbit.domain.rabbit.policy

import io.github.gunkim.rabbit.domain.Position

class CreateRandomPositionPolicy : CreatePositionPolicy {
    override fun position() = Position((MIN_POSITION..MAX_POSITION).random())

    companion object {
        private const val MIN_POSITION = 0
        private const val MAX_POSITION = 5
    }
}
