package io.github.gunkim.blackjack.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("딜러는")
class DealerTest {
    private Dealer sut;

    @BeforeEach
    void setup() {
        var dummyCardDeck = new CardDeck(Card.distinctCards());
        this.sut = new Dealer(dummyCardDeck);
    }

    @Test
    @DisplayName("플레이어에게 카드를 나눠준다")
    void shouldDeal() {
        var player = sut.deal();
        assertNotNull(player);
    }
}
