package io.github.gunkim.blackjack.domain;

import java.util.*;
import java.util.stream.IntStream;

public class CardDeck {
    private static final int DEFAULT_CARD_SIZE = 52;

    private final ArrayDeque<Card> cards;

    CardDeck(Collection<Card> cards) {
        validateCards(cards);
        this.cards = new ArrayDeque<>(cards);
    }

    public static CardDeck shuffle() {
        List<Card> shuffledCards = new ArrayList<>(Card.distinctCards());

        Collections.shuffle(shuffledCards);
        return new CardDeck(shuffledCards);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck");
        }
        return cards.remove();
    }

    public List<Card> draw(int quantity) {
        return IntStream.range(0, quantity)
                .mapToObj(__ -> draw())
                .toList();
    }

    private void validateCards(Collection<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");
        checkForDuplicateCards(cards);
        validateCardDeckSize(cards);
    }

    private void checkForDuplicateCards(Collection<Card> cards) {
        var containsDuplicateCards = new HashSet<>(cards).size() != cards.size();
        if (containsDuplicateCards) {
            throw new IllegalArgumentException("cards must not contain duplicate cards");
        }
    }

    private void validateCardDeckSize(Collection<Card> cards) {
        if (cards.size() != DEFAULT_CARD_SIZE) {
            throw new IllegalArgumentException(String.format("cards must contain %d cards", DEFAULT_CARD_SIZE));
        }
    }
}
