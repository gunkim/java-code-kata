package io.github.gunkim.baseball;

import io.github.gunkim.baseball.application.ConsoleInput;
import io.github.gunkim.baseball.application.ConsoleOutput;
import io.github.gunkim.baseball.application.DefaultInputScanner;
import io.github.gunkim.baseball.application.RandomNumberInput;
import io.github.gunkim.baseball.domain.BaseBallGame;

public class Application {
    private static final int TOTAL_BALL_COUNT = 3;
    private static final boolean SHOULD_DISPLAY_ANSWER = true;

    public static void main(final String[] args) {
        new BaseBallGame(
                new ConsoleInput(new DefaultInputScanner(), TOTAL_BALL_COUNT),
                new RandomNumberInput(TOTAL_BALL_COUNT),
                new ConsoleOutput(),
                TOTAL_BALL_COUNT,
                SHOULD_DISPLAY_ANSWER
        ).start();
    }
}