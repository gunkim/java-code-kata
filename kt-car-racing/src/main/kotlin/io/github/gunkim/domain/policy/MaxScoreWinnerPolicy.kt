package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car

class MaxScoreWinnerPolicy : WinnerPolicy {
    override fun winner(cars: List<Car>): List<Car> {
        val maxForward: Int = cars.maxBy(Car::forward).forward
        return cars
            .filter { it.forward == maxForward }
            .toList()
    }
}