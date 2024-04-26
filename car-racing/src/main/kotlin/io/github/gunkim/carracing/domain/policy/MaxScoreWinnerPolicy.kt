package io.github.gunkim.carracing.domain.policy

import io.github.gunkim.carracing.domain.car.Car
import io.github.gunkim.carracing.domain.car.Cars

class MaxScoreWinnerPolicy : WinnerPolicy {
    override fun winner(cars: List<Car>) =
        Cars(
            cars.groupBy { it.forwardValue }
                .maxBy { it.key }
                .value
        )
}
