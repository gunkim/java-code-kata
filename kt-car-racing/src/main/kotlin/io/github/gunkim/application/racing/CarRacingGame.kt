package io.github.gunkim.application.racing

import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Output
import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput
import io.github.gunkim.domain.car.Car
import io.github.gunkim.domain.car.CarRaceTrack
import io.github.gunkim.domain.car.Cars
import io.github.gunkim.domain.car.vo.Lab
import io.github.gunkim.domain.car.vo.Name
import io.github.gunkim.domain.policy.MaxScoreWinnerPolicy
import io.github.gunkim.domain.policy.MovePolicy
import io.github.gunkim.domain.policy.RandomMovePolicy
import io.github.gunkim.domain.policy.WinnerPolicy

class CarRacingGame(
    private val input: Input,
    private val output: Output,
    private val movePolicy: MovePolicy,
    private val winnerPolicy: WinnerPolicy,
) {
    fun run() {
        output.carnameInputMessage()
        val track = createTrack(cars(input.carname), movePolicy, winnerPolicy)
            .also { output.maxRoundInputMessage() }
            .run { round(Lab(input.maxRound)) }

        output.dashboard(track.carList)
        output.winners(track.winners)
    }

    private fun createTrack(
        cars: Cars,
        movePolicy: MovePolicy,
        winnerPolicy: WinnerPolicy,
    ) = CarRaceTrack(
        cars,
        movePolicy,
        winnerPolicy
    )

    private fun cars(line: String) = Cars(
        line.let(::parseString)
            .let(::convertCar)
    )

    private fun parseString(carNames: String, delimiters: String = ","): List<String> =
        carNames.split(delimiters)
            .filter(String::isNotBlank)
            .map(String::removeBlank)

    private fun convertCar(cars: List<String>) =
        cars.map(::Name)
            .map(::Car)

    companion object {
        fun default() = CarRacingGame(
            ConsoleInput(),
            ConsoleOutput(),
            RandomMovePolicy(),
            MaxScoreWinnerPolicy()
        )
    }
}

private fun String.removeBlank() = this.replace(" ", "")
