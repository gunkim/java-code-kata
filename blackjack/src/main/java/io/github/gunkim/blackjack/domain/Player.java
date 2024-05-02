package io.github.gunkim.blackjack.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Player {
    //TODO: 일급 컬렉션 및 별도의 객체로 분리될 수 있을 것 같음. 혹은 CardDeck이 이 역할을 어느정도 할 수도 있음.
    private final Set<Card> cards;

    public Player(Collection<Card> cards) {
        Objects.requireNonNull(cards, "cards must not be null");

        if (cards.size() != Dealer.CARD_DEAL) {
            throw new IllegalArgumentException("Player must have exactly %d cards".formatted(Dealer.CARD_DEAL));
        }
        this.cards = new HashSet<>(cards);
    }
}
