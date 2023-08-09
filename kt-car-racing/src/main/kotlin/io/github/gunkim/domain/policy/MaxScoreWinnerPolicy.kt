package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car
import io.github.gunkim.domain.car.Cars

class MaxScoreWinnerPolicy : WinnerPolicy {
    override fun winner(cars: List<Car>): Cars {
        return Cars(
            cars.groupBy { it.forwardValue }
                .maxBy { it.key }
                .value
        )
    }
}
