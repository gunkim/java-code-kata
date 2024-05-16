package io.github.gunkim.blackjack.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Player {
    private final Set<Card> cards;

    public Player(Collection<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");

        if (cards.size() != Dealer.CARD_DEAL) {
            throw new IllegalArgumentException("Player must have exactly %d cards".formatted(Dealer.CARD_DEAL));
        }
        this.cards = new HashSet<>(cards);
    }

    public Set<Card> cards() {
        return Set.copyOf(cards);
    }

    public void hit(Card drawCard) {
        cards.add(drawCard);
    }
}
