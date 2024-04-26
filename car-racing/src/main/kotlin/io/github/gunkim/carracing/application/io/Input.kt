package io.github.gunkim.carracing.application.io

import io.github.gunkim.carracing.domain.car.Cars

interface Input {
    val cars: Cars
    val maxRound: Int
}
