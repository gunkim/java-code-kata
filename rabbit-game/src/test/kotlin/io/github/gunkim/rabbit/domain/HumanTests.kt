package io.github.gunkim.rabbit.domain

import io.github.gunkim.rabbit.domain.rabbit.Rabbit
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.UUID

private val humanId: UUID = UUID.randomUUID()

@DisplayName("사람은")
class HumanTests : StringSpec({
    "토끼와 위치가 같을 경우 토끼를 잡을 수 있다." {
        val human = Human(humanId, Position(0))
        val rabbit = Rabbit(position = Position(0))

        human.catch(rabbit) shouldBe true
    }
    "왼쪽으로 움직일 수 있다." {
        val human = Human(humanId, Position(10))

        val result = human.leftMove(Position(3))

        result shouldBe Human(humanId, Position(7))
    }
    "오른쪽으로 움직일 수 있다." {
        val human = Human(humanId, Position(10))

        val result = human.rightMove(Position(3))

        result shouldBe Human(humanId, Position(13))
    }
    "왼쪽으로 한번에 10칸을 초과해서 이동할 경우 예외가 발생한다." {
        val human = Human(humanId, Position(15))

        shouldThrow<IllegalArgumentException> { human.leftMove(Position(11)) }
    }
    "오른쪽으로 한번에 10칸을 초과해서 이동할 경우 예외가 발생한다." {
        val human = Human(humanId, Position(15))

        shouldThrow<IllegalArgumentException> { human.rightMove(Position(11)) }
    }
})
