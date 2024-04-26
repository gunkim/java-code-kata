package io.github.gunkim.carracing.application.io

import io.github.gunkim.carracing.domain.car.Cars

interface Output {
    fun carnameInputMessage()
    fun maxRoundInputMessage()
    fun dashboard(cars: Cars)
    fun winners(winners: Cars)
}
