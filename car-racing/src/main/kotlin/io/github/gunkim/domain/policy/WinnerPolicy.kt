package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car
import io.github.gunkim.domain.car.Cars

fun interface WinnerPolicy {
    fun winner(cars: List<Car>): Cars
}
