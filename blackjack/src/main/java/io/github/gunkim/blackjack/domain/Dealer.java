package io.github.gunkim.blackjack.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Dealer {
    public static final int CARD_DEAL = 2;

    private final CardDeck deck;
    private final Set<Card> cards;

    public Dealer(CardDeck deck) {
        Objects.requireNonNull(deck, "deck must not be null");

        this.deck = deck;
        this.cards = new HashSet<>(deck.draw(CARD_DEAL));
    }

    public Player deal() {
        return new Player(deck.draw(CARD_DEAL));
    }

    public void hit(Player player) {
        player.hit(deck.draw());
    }
}
