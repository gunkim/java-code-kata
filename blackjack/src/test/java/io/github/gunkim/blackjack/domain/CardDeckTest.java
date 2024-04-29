package io.github.gunkim.blackjack.domain;

import io.github.gunkim.blackjack.domain.vo.CardNumber;
import io.github.gunkim.blackjack.domain.vo.CardSuit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("카드 덱은")
class CardDeckTest {
    @Test
    @DisplayName("생성된다")
    void shouldCreateCardDeck() {
        List<Card> cards = Card.distinctCards();

        assertDoesNotThrow(() -> new CardDeck(cards));
    }

    @Test
    @DisplayName("섞인 채로 생성된다")
    void shouldCreateShuffledDeck() {
        CardDeck cardDeck = CardDeck.shuffle();

        assertNotNull(cardDeck);
    }

    @Test
    @DisplayName("중복된 카드가 없을 경우 생성된다")
    void shouldCreateCardDeckWhenNotDuplicatedCards() {
        List<Card> cards = Card.distinctCards();

        assertDoesNotThrow(() -> new CardDeck(cards));
    }

    @Test
    @DisplayName("중복된 카드가 있을 경우 생성되지 않는다")
    void shouldFailToCreateCardDeckWhenDuplicatedCards() {
        List<Card> cards = new ArrayList<>(Card.distinctCards());
        cards.removeLast();
        cards.add(cards.get(0));

        assertThrows(IllegalArgumentException.class, () -> new CardDeck(cards));
    }

    @Test
    @DisplayName("카드가 52장이 아닐 경우 예외가 발생한다")
    void shouldThrowExceptionWhenCardCountIsNot52() {
        List<Card> cards = List.of(new Card(CardSuit.CLUBS, CardNumber.A));

        String actualErrorMessage = assertThrows(IllegalArgumentException.class, () -> new CardDeck(cards)).getMessage();
        assertEquals(actualErrorMessage, "cards must contain 52 cards");
    }

    @Test
    @DisplayName("덱에 카드가 남아 있다면 카드를 뽑을 수 있어야 한다")
    void shouldDrawCardWhenDeckIsNotEmpty() {
        CardDeck cardDeck = CardDeck.shuffle();
        Card card = cardDeck.draw();

        assertNotNull(card);
    }

    @Test
    @DisplayName("덱에 카드가 비어 있다면 카드를 뽑을 수 없다")
    void shouldThrowExceptionWhenCardDeckIsEmpty() {
        CardDeck cardDeck = CardDeck.shuffle();
        IntStream.range(0, 52).forEach(__ -> cardDeck.draw());

        String actualErrorMessage = assertThrows(IllegalStateException.class, cardDeck::draw).getMessage();
        assertEquals(actualErrorMessage, "No more cards in the deck");
    }
}
