package io.github.gunkim.rabbit.domain.rabbit

import io.github.gunkim.rabbit.domain.Direction
import io.github.gunkim.rabbit.domain.Position
import io.github.gunkim.rabbit.domain.rabbit.policy.CreatePositionPolicy
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.*

private val rabbitId: UUID = UUID.randomUUID()
private val createPositionPolicy = CreatePositionPolicy { Position(1) }

@DisplayName("토끼는")
class RabbitTests : StringSpec({
    "왼쪽으로 움직일 수 있다." {
        val rabbit = Rabbit(rabbitId, Position(5))

        val result = rabbit.move(createPositionPolicy) { Direction.LEFT }

        result shouldBe Rabbit(rabbitId, Position(4))
    }
    "오른쪽으로 움직일 수 있다." {
        val rabbit = Rabbit(rabbitId, Position(5))

        val result = rabbit.move(createPositionPolicy) { Direction.RIGHT }

        result shouldBe Rabbit(rabbitId, Position(6))
    }
    "움직여야 할 위치가 범위를 벗어날 경우 움직이지 않는다." {
        val rabbit = Rabbit(rabbitId, Position(0))

        val result = rabbit.move(createPositionPolicy) { Direction.LEFT }

        result shouldBe Rabbit(rabbitId, Position(0))
    }
})
