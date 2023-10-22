package io.github.gunkim.rabbit.domain.rabbit

import io.github.gunkim.rabbit.domain.Direction
import io.github.gunkim.rabbit.domain.Direction.LEFT
import io.github.gunkim.rabbit.domain.Direction.RIGHT
import io.github.gunkim.rabbit.domain.Position
import io.github.gunkim.rabbit.domain.rabbit.policy.CreateDirectionPolicy
import io.github.gunkim.rabbit.domain.rabbit.policy.CreatePositionPolicy
import java.util.*

data class Rabbit(
    val id: UUID = UUID.randomUUID(),
    val position: Position = Position.random()
) {
    val positionValue: Int
        get() = position.value

    fun move(
        createPositionPolicy: CreatePositionPolicy,
        createDirectionPolicy: CreateDirectionPolicy
    ): Rabbit {
        val position = createPositionPolicy.position()

        return when (val direction = createDirectionPolicy.direction()) {
            LEFT -> {
                if (!isMove(position, direction)) {
                    return this
                }
                Rabbit(id, this.position - position)
            }

            RIGHT -> {
                if (!isMove(position, direction)) {
                    return this
                }
                Rabbit(id, this.position + position)
            }
        }
    }

    fun isSamePosition(position: Position) = this.position == position


    private fun isMove(position: Position, direction: Direction) = when (direction) {
        LEFT -> this.position.minus(position).value >= MIN_POSITION
        RIGHT -> this.position.plus(position).value <= MAX_POSITION
    }

    companion object {
        private const val MIN_POSITION = 0
        private const val MAX_POSITION = 69
    }
}
