package io.github.gunkim.domain.car

import io.github.gunkim.domain.policy.MovePolicy
import io.github.gunkim.domain.policy.WinnerPolicy

data class CarRaceTrack(
    private val cars: Cars,
    private val movePolicy: MovePolicy,
    private val winnerPolicy: WinnerPolicy,
) {
    val winners: List<Car>
        get() = winnerPolicy.winner(cars.list)
    val carList: List<Car>
        get() = cars.list

    fun round(max: Int): CarRaceTrack {
        var cars = cars
        for (i in 0..max) {
            cars = cars.go(movePolicy)
        }
        return CarRaceTrack(cars, movePolicy, winnerPolicy)
    }
}
