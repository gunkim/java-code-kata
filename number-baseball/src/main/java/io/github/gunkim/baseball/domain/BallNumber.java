package io.github.gunkim.baseball.domain;

public enum BallNumber {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private final int number;

    BallNumber(final int number) {
        this.number = number;
    }

    public static BallNumber of(final int number) {
        for (BallNumber ballNumber : values()) {
            if (ballNumber.number() == number) {
                return ballNumber;
            }
        }
        throw new IllegalArgumentException("Invalid BallNumber: " + number);
    }

    public int number() {
        return number;
    }
}
