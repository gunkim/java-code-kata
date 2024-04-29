package io.github.gunkim.blackjack.domain;

import io.github.gunkim.blackjack.domain.vo.CardNumber;
import io.github.gunkim.blackjack.domain.vo.CardSuit;

import java.util.List;
import java.util.stream.Stream;

public record Card(CardSuit suit, CardNumber number) {
    private static final List<Card> ALL_CARDS;

    static {
        final CardSuit[] cardSuits = CardSuit.values();
        final CardNumber[] cardNumbers = CardNumber.values();

        ALL_CARDS = Stream.of(cardSuits)
                .flatMap(suit -> Stream.of(cardNumbers).map(number -> new Card(suit, number)))
                .toList();
    }

    /**
     * 이 함수를 사용할 경우 새로운 Card 객체를 생성하지 않아 메모리 사용이 최적화됩니다.
     */
    public static Card of(CardSuit suit, CardNumber number) {
        return ALL_CARDS.stream()
                .filter(card -> card.suit() == suit && card.number() == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid card: %s %s".formatted(suit, number)));
    }

    /**
     * @return ImmutableCollections 객체를 반환합니다.
     */
    public static List<Card> distinctCards() {
        return ALL_CARDS;
    }
}
