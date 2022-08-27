package io.github.gunkim.domain.car

import io.github.gunkim.domain.policy.MovePolicy
import io.github.gunkim.domain.policy.WinnerPolicy

class CarRaceTrack(
    val cars: List<Car>,
    private val movePolicy: MovePolicy,
    private val winnerPolicy: WinnerPolicy,
) {
    init {
        require(cars.isNotEmpty()) {
            "자동차가 최소 1대는 존재해야 합니다."
        }
    }

    private var isRound = false
    val winners: List<Car>
        get() {
            require(isRound) { "레이싱이 시작되지 않았습니다." }

            return winnerPolicy.winner(cars)
        }

    fun round(max: Int): Unit = repeat(max) {
        cars.forEach { if (movePolicy.isMove()) it.go() }
    }.also { isRound = true }

    companion object {
        private fun generateCars(line: String, delimiters: String = ","): List<Car> =
            line.split(delimiters)
                .filter(String::isNotBlank)
                .map(String::removeBlank)
                .map(::Car)
                .toList()

        fun of(line: String, movePolicy: MovePolicy, winnerPolicy: WinnerPolicy, delimiters: String = ","): CarRaceTrack =
            CarRaceTrack(
                generateCars(line, delimiters),
                movePolicy,
                winnerPolicy
            )
    }
}

private fun String.removeBlank() = this.replace(" ", "")