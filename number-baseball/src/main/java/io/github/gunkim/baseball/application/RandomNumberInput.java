package io.github.gunkim.baseball.application;


import io.github.gunkim.baseball.domain.BallNumber;
import io.github.gunkim.baseball.domain.BallNumbers;
import io.github.gunkim.baseball.domain.Input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomNumberInput implements Input {
    private final BallNumbers ballNumbers;

    public RandomNumberInput(final BallNumbers ballNumbers) {
        this.ballNumbers = ballNumbers;
    }

    public RandomNumberInput(final int ballCount) {
        this(new BallNumbers(generateRandomBallNumbers(ballCount)));
    }

    private static List<BallNumber> generateRandomBallNumbers(final int ballCount) {
        List<BallNumber> ballNumbers = new ArrayList<>(Arrays.stream(BallNumber.values()).toList());
        Collections.shuffle(ballNumbers);
        return ballNumbers.subList(0, ballCount);
    }

    @Override
    public BallNumbers ballNumbers() {
        return ballNumbers;
    }
}