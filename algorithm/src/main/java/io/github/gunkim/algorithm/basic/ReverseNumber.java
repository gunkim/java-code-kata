package io.github.gunkim.algorithm.basic;

public class ReverseNumber {
    private final int value;

    public ReverseNumber(int value) {
        this.value = value;
    }

    public int reverse() {
        if (value == 0) {
            return value;
        }
        int number = Math.abs(value);
        int reversed = 0;
        while (number > 0) {
            reversed = (reversed * 10) + (number % 10);
            number /= 10;
        }

        return value < 0 ? -reversed : reversed;
    }
}
