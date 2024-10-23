package io.github.gunkim.baseball.application;

import io.github.gunkim.baseball.domain.BallNumber;
import io.github.gunkim.baseball.domain.BallNumbers;
import io.github.gunkim.baseball.domain.Input;

import java.util.Scanner;

public class ConsoleInput implements Input {
    private static final Scanner SCANNER = new Scanner(System.in);

    private final int ballCount;

    public ConsoleInput(final int ballCount) {
        this.ballCount = ballCount;
    }

    @Override
    public BallNumbers ballNumbers() {
        final String input = SCANNER.nextLine();
        if (input.length() != ballCount) {
            throw new IllegalArgumentException("3자리 숫자를 입력해주세요.");
        }
        if (input.chars().distinct().count() != ballCount) {
            throw new IllegalArgumentException("중복되지 않는 숫자를 입력해주세요.");
        }
        if (input.chars().anyMatch(c -> c < '1' || c > '9')) {
            throw new IllegalArgumentException("1부터 9까지의 숫자를 입력해주세요.");
        }
        return new BallNumbers(input.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .map(Integer::parseInt)
                .map(BallNumber::of)
                .toList());
    }
}
