package io.github.gunkim.blackjack.domain;

import io.github.gunkim.blackjack.domain.vo.CardNumber;
import io.github.gunkim.blackjack.domain.vo.CardSuit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("플레이어는")
class PlayerTest {
    @Test
    @DisplayName("카드 2장을 받았을 때 정상적으로 생성된다")
    void shouldCreatePlayer() {
        List<Card> cards = List.of(
                Card.of(CardSuit.HEARTS, CardNumber.A),
                Card.of(CardSuit.HEARTS, CardNumber.K)
        );
        assertDoesNotThrow(() -> new Player(cards));
    }

    @ParameterizedTest
    @MethodSource("cardProvider")
    @DisplayName("카드가 2장이 아닐 경우 생성되지 않는다")
    void shouldThrowExceptionWhenCardSizeIsNotTwo(List<Card> cards) {
        var actualMessage = assertThrows(IllegalArgumentException.class, () -> new Player(cards)).getMessage();

        assertEquals(actualMessage, "Player must have exactly %d cards".formatted(Dealer.CARD_DEAL));
    }

    private static Stream<Arguments> cardProvider() {
        return Stream.of(
                Arguments.of(List.of()),
                Arguments.of(List.of(Card.of(CardSuit.CLUBS, CardNumber.A))),
                Arguments.of(List.of(
                        Card.of(CardSuit.CLUBS, CardNumber.A),
                        Card.of(CardSuit.HEARTS, CardNumber.K),
                        Card.of(CardSuit.HEARTS, CardNumber.K)
                ))
        );
    }
}
