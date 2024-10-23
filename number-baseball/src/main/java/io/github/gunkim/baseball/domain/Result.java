package io.github.gunkim.baseball.domain;

public record Result(
        int strike,
        int ball
) {
    public boolean isAllStrike(final int totalBallCount) {
        return strike == totalBallCount;
    }

    public boolean isNothing() {
        return strike == 0 && ball == 0;
    }
}
