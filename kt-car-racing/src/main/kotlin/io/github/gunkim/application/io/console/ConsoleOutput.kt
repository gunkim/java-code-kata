package io.github.gunkim.application.io.console

import io.github.gunkim.application.io.Output
import io.github.gunkim.domain.car.Car

class ConsoleOutput : Output {
    override fun carnameInputMessage() = println("경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).")

    override fun maxRoundInputMessage() = println("시도할 회수는 몇회인가요?")

    override fun dashboard(cars: List<Car>) = cars.forEach { (name, forward) ->
        val distance = IntRange(0, forward).joinToString("") { "-" }
        println("$name : $distance")
    }

    override fun winners(winners: List<Car>) {
        val queue = ArrayDeque(winners)
        print(queue.removeFirst().name)
        while (queue.isNotEmpty()) {
            print(", ${queue.removeFirst().name}")
        }
        println("가 최종 우승했습니다.")
    }
}