package io.github.gunkim.baseball.domain;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class BallNumbers {
    private final List<BallNumber> ballNumbers;

    public BallNumbers(final List<BallNumber> ballNumbers) {
        this.ballNumbers = ballNumbers;
    }

    public Result match(final BallNumbers otherBalls) {
        int strike = 0;
        int ball = 0;

        for (int i = 0; i < this.ballNumbers.size(); i++) {
            final BallNumber myBall = this.ballNumbers.get(i);
            final BallNumber otherBall = otherBalls.ballNumbers.get(i);

            if (myBall.equals(otherBall)) {
                strike++;
            } else if (hasBallNumber(myBall)) {
                ball++;
            }
        }

        return new Result(strike, ball);
    }

    private boolean hasBallNumber(final BallNumber ballNumber) {
        return this.ballNumbers.contains(ballNumber);
    }

    @Override
    public String toString() {
        return ballNumbers.stream()
                .map(BallNumber::number)
                .map(String::valueOf)
                .collect(joining());
    }
}