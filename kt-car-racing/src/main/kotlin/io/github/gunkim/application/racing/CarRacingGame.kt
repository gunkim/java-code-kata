package io.github.gunkim.application.racing

import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Output
import io.github.gunkim.application.io.console.ConsoleInput
import io.github.gunkim.application.io.console.ConsoleOutput
import io.github.gunkim.domain.car.CarRaceTrack
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
        val application = CarRaceTrack.of(input.carname, movePolicy, winnerPolicy)

        output.maxRoundInputMessage()
        application.round(input.maxRound)

        output.dashboard(application.carList)
        output.winners(application.winners)
    }

    companion object {
        fun default(): CarRacingGame {
            return CarRacingGame(
                ConsoleInput(),
                ConsoleOutput(),
                RandomMovePolicy(),
                MaxScoreWinnerPolicy()
            )
        }
    }
}
