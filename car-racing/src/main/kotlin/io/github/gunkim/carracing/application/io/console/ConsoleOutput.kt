package io.github.gunkim.carracing.application.io.console

import io.github.gunkim.carracing.application.io.Output
import io.github.gunkim.carracing.domain.car.Car
import io.github.gunkim.carracing.domain.car.Cars

class ConsoleOutput : Output {
    override fun carnameInputMessage() = println("경주할 자동차 이름을 입력하세요(이름은 쉼표(,)를 기준으로 구분).")

    override fun maxRoundInputMessage() = println("시도할 회수는 몇회인가요?")

    override fun dashboard(cars: Cars) = cars.list.forEach { (name, forward) ->
        val distance = IntRange(0, forward.value).joinToString("") { "-" }
        println("${name.value} : $distance")
    }

    override fun winners(winners: Cars) {
        val carNames = ArrayDeque(winners.list).joinToString(", ", transform = Car::nameValue)
        println("${carNames}가 최종 우승했습니다.")
    }
}

private val ArrayDeque<Car>.car: Car
    get() = this.removeFirst()
