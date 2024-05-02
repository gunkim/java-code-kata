package io.github.gunkim.blackjack.domain;

public record Dealer(CardDeck deck) {
    //TODO: 최초 블랙잭 시작 시 딜러는 플레이어에게 2장의 카드를 나눠줘야 함을 변수로 나타냄. 더 적절한 네이밍이 필요함.
    public static final int CARD_DEAL = 2;

    public Player deal() {
        return new Player(deck.draw(CARD_DEAL));
    }
}
