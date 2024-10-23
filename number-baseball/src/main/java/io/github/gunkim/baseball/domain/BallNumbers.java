package io.github.gunkim.baseball.domain;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class BallNumbers {
    private final List<BallNumber> ballNumbers;

    public BallNumbers(List<BallNumber> ballNumbers) {
        this.ballNumbers = ballNumbers;
    }

    public Result match(final BallNumbers otherBalls) {
        int strike = 0, ball = 0;

        for (int i = 0; i < this.ballNumbers.size(); i++) {
            final BallNumber userBall = this.ballNumbers.get(i);
            final BallNumber computerBall = otherBalls.ballNumbers.get(i);

            if (userBall.equals(computerBall)) {
                strike++;
            } else if (this.hasBallNumber(userBall)) {
                ball++;
            }
        }

        return new Result(strike, ball);
    }

    private boolean hasBallNumber(BallNumber ballNumber) {
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