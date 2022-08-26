package io.github.gunkim.application.racing

import io.github.gunkim.application.io.Input
import io.github.gunkim.application.io.Mode
import io.github.gunkim.application.io.Output
import io.github.gunkim.domain.car.CarRaceTrack
import io.github.gunkim.domain.policy.MaxScoreWinnerPolicy
import io.github.gunkim.domain.policy.MovePolicy
import io.github.gunkim.domain.policy.RandomMovePolicy
import io.github.gunkim.domain.policy.WinnerPolicy

class CarRacingGame(
    mode: Mode,
    private val movePolicy: MovePolicy,
    private val winnerPolicy: WinnerPolicy,
) {
    private val input: Input = mode.input
    private val output: Output = mode.output

    fun run() {
        output.carNameInput()
        val application = CarRaceTrack.of(input.input(), movePolicy, winnerPolicy)

        output.roundInput()
        application.round(input.inputInt())

        output.dashboard(application.cars)
        output.winners(application.winners)
    }

    companion object {
        fun default(): CarRacingGame {
            return CarRacingGame(
                Mode.CONSOLE,
                RandomMovePolicy(),
                MaxScoreWinnerPolicy()
            )
        }
    }
}
