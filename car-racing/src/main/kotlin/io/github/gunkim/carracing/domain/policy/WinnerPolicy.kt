package io.github.gunkim.carracing.domain.policy

import io.github.gunkim.carracing.domain.car.Car
import io.github.gunkim.carracing.domain.car.Cars

fun interface WinnerPolicy {
    fun winner(cars: List<Car>): Cars
}
