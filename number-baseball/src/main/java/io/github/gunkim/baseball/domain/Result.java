package io.github.gunkim.baseball.domain;

public record Result(
        int strike,
        int ball
) {
    public boolean isAllStrike() {
        return ball == 0 && strike > 0;
    }

    public boolean isNothing() {
        return strike == 0 && ball == 0;
    }
}
