package io.github.gunkim.application.io.console

import io.github.gunkim.application.io.Input
import io.github.gunkim.domain.car.Car
import io.github.gunkim.domain.car.Cars
import io.github.gunkim.domain.car.vo.Name

class ConsoleInput : Input {
    override val cars: Cars
        get() = readlnOrNull()
            ?.let(::parseString)
            ?.let(::convertCar)
            ?.let(::Cars)
            ?: throw IllegalArgumentException("무조건 입력해주셔야 댐")
    override val maxRound: Int
        get() = readln().toInt()

    private fun parseString(carNames: String, delimiters: String = ","): List<String> =
        carNames.split(delimiters)
            .filter(String::isNotBlank)
            .map(String::removeBlank)

    private fun convertCar(cars: List<String>) =
        cars.map(::Name)
            .map(::Car)
}

private fun String.removeBlank() = this.replace(" ", "")
