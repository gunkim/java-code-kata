package io.github.gunkim.rabbit.domain

import io.github.gunkim.rabbit.domain.rabbit.Rabbit
import java.util.UUID

data class Human(
    val id: UUID = UUID.randomUUID(),
    val position: Position = Position.random(),
) {
    val positionValue: Int
        get() = position.value

    fun catch(rabbit: Rabbit): Boolean = rabbit.isSamePosition(this.position)

    fun move(direction: Direction, position: Position): Human {
        return when (direction) {
            Direction.LEFT -> Human(id, this.position - position)
            Direction.RIGHT -> Human(id, this.position + position)
        }
    }

    fun leftMove(position: Position): Human {
        validateRange(position)

        return Human(id, this.position - position)
    }

    fun rightMove(position: Position): Human {
        validateRange(position)

        return Human(id, this.position + position)
    }

    private fun validateRange(position: Position) {
        require(position.value <= 10) { "한번에 10칸을 초과해서 이동할 수 없습니다." }
    }
}
