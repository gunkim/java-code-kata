package io.github.gunkim.application.io

import io.github.gunkim.domain.car.Cars

interface Output {
    fun carnameInputMessage()
    fun maxRoundInputMessage()
    fun dashboard(cars: Cars)
    fun winners(winners: Cars)
}
