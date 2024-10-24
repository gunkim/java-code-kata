package io.github.gunkim.baseball.application;

import io.github.gunkim.baseball.domain.BallNumber;
import io.github.gunkim.baseball.domain.BallNumbers;
import io.github.gunkim.baseball.domain.Input;

public class ConsoleInput implements Input {
    private final InputScanner inputScanner;
    private final int ballCount;

    public ConsoleInput(final InputScanner inputScanner, final int ballCount) {
        this.inputScanner = inputScanner;
        this.ballCount = ballCount;
    }

    @Override
    public BallNumbers ballNumbers() {
        final String input = inputScanner.input();

        //TODO: 입력한 값에 대한 유효성 검증은 여기서 처리하는게 맞을까? 혹은 도메인 로직일까?
        if (input.length() != ballCount) {
            throw new IllegalArgumentException("%d자리 숫자를 입력해주세요. 현재: %s".formatted(ballCount, input));
        }
        if (input.chars().distinct().count() != ballCount) {
            throw new IllegalArgumentException("중복되지 않는 숫자를 입력해주세요. 현재: %s".formatted(input));
        }
        if (input.chars().anyMatch(c -> c < '1' || c > '9')) {
            throw new IllegalArgumentException("1부터 9까지의 숫자를 입력해주세요. 현재: %s".formatted(input));
        }
        return new BallNumbers(input.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .map(Integer::parseInt)
                .map(BallNumber::of)
                .toList());
    }
}
