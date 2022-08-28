package io.github.gunkim.domain.car

import io.github.gunkim.domain.policy.MovePolicy
import io.github.gunkim.domain.policy.WinnerPolicy

class CarRaceTrack(
    private val cars: Cars,
    private val movePolicy: MovePolicy,
    private val winnerPolicy: WinnerPolicy,
) {
    private var isRound = false
    val winners: List<Car>
        get() {
            require(isRound) { "레이싱이 시작되지 않았습니다." }

            return winnerPolicy.winner(cars.list)
        }
    val carList: List<Car>
        get() = cars.list

    fun round(max: Int): Unit = repeat(max) { cars.go(movePolicy) }
        .also { isRound = true }

    companion object {
        fun of(line: String, movePolicy: MovePolicy, winnerPolicy: WinnerPolicy, delimiters: String = ","): CarRaceTrack =
            CarRaceTrack(
                Cars.from(line, delimiters),
                movePolicy,
                winnerPolicy
            )
    }
}
