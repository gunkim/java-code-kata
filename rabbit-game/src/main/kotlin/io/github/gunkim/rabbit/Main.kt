package io.github.gunkim.rabbit

import io.github.gunkim.rabbit.application.MoveInput
import io.github.gunkim.rabbit.domain.Human
import io.github.gunkim.rabbit.domain.rabbit.Rabbit
import io.github.gunkim.rabbit.domain.rabbit.policy.CreateRandomDirectionPolicy
import io.github.gunkim.rabbit.domain.rabbit.policy.CreateRandomPositionPolicy

private val createPositionPolicy = CreateRandomPositionPolicy()
private val createDirectionPolicy = CreateRandomDirectionPolicy()

fun main() {
    var human = Human()
    var rabbit = Rabbit()

    while (true) {
        if (human.catch(rabbit)) {
            println("토끼를 포획했습니다!")
            break
        }
        println("이동할 위치를 알려주세요.")
        val (position, direction) = MoveInput(readln())

        human = human.move(direction, position)
        rabbit = rabbit.move(createPositionPolicy, createDirectionPolicy)

        println("토끼 위치: ${rabbit.positionValue}")
        println("사람 위치: ${human.positionValue}")
    }
}
