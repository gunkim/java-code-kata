package io.github.gunkim.application.io

import io.github.gunkim.domain.car.Car

interface Output {
    fun carnameInputMessage()
    fun maxRoundInputMessage()
    fun dashboard(cars: List<Car>)
    fun winners(winners: List<Car>)
}