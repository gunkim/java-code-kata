package io.github.gunkim.domain.policy

import io.github.gunkim.domain.car.Car

fun interface WinnerPolicy {
    fun winner(cars: List<Car>): List<Car>
}