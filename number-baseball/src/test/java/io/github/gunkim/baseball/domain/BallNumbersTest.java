package io.github.gunkim.baseball.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("BallNumbers는")
class BallNumbersTest {

    @Test
    void 모든_숫자가_같다면_올_스트라이크_노_낫싱이다() {
        final List<BallNumber> numbers = Stream.of(1, 2, 3)
                .map(BallNumber::of)
                .toList();

        final var totalBallCount = 3;
        final var ballNumbers = new BallNumbers(numbers);
        final var otherBallNumbers = new BallNumbers(numbers);

        final Result result = ballNumbers.match(otherBallNumbers);

        assertAll(
                () -> assertTrue(result.isAllStrike(totalBallCount)),
                () -> assertFalse(result.isNothing())
        );
    }

    @Test
    void 모든_숫자가_다르다면_낫싱_노_스트라이크이다() {
        final var ballNumbers = new BallNumbers(Stream.of(1, 2, 3)
                .map(BallNumber::of)
                .toList());
        final var otherBallNumbers = new BallNumbers(Stream.of(4, 5, 6)
                .map(BallNumber::of)
                .toList());

        final Result result = ballNumbers.match(otherBallNumbers);

        assertAll(
                () -> assertTrue(result.isNothing()),
                () -> assertFalse(result.isAllStrike(3))
        );
    }

    @Test
    void 숫자123과_숫자231은_1스트라이크_2볼이다() {
        final var ballNumbers = new BallNumbers(Stream.of(1, 2, 3)
                .map(BallNumber::of)
                .toList());
        final var otherBallNumbers = new BallNumbers(Stream.of(1, 3, 2)
                .map(BallNumber::of)
                .toList());

        final Result result = ballNumbers.match(otherBallNumbers);

        assertAll(
                () -> assertEquals(1, result.strike()),
                () -> assertEquals(2, result.ball()),
                () -> assertFalse(result.isNothing()),
                () -> assertFalse(result.isAllStrike(3))
        );
    }
}
