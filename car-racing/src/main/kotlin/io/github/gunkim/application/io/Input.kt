package io.github.gunkim.application.io

import io.github.gunkim.domain.car.Cars

interface Input {
    val cars: Cars
    val maxRound: Int
}
