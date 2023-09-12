package io.github.gunkim.application.racing

import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Output
import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput
import io.github.gunkim.domain.car.CarRaceTrack
import io.github.gunkim.domain.car.vo.Lab
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

        val track = CarRaceTrack(input.cars, movePolicy, winnerPolicy)
            .also { output.maxRoundInputMessage() }
            .run { round(Lab(input.maxRound)) }

        output.dashboard(track.cars)
        output.winners(track.winners)
    }

    companion object {
        fun default() = CarRacingGame(
            ConsoleInput(),
            ConsoleOutput(),
            RandomMovePolicy(),
            MaxScoreWinnerPolicy(),
        )
    }
}
