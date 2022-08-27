package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car

class MaxScoreWinnerPolicy : WinnerPolicy {
    override fun winner(cars: List<Car>): List<Car> {
        return cars.groupBy { it.forward }
            .maxBy { it.key }
            .value
    }
}