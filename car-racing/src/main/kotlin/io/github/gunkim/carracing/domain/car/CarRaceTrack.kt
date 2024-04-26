package io.github.gunkim.carracing.domain.car

import io.github.gunkim.carracing.domain.car.vo.Lab
import io.github.gunkim.carracing.domain.policy.MovePolicy
import io.github.gunkim.carracing.domain.policy.WinnerPolicy

data class CarRaceTrack(
    val cars: Cars,
    private val movePolicy: MovePolicy,
    private val winnerPolicy: WinnerPolicy
) {
    val winners: Cars
        get() = winnerPolicy.winner(cars.list)

    fun round(lab: Lab): CarRaceTrack {
        var cars = cars
        repeat(lab.value) {
            cars = cars.go(movePolicy)
        }
        return CarRaceTrack(cars, movePolicy, winnerPolicy)
    }
}
