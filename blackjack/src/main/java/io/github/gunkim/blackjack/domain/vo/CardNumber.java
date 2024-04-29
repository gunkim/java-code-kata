package io.github.gunkim.blackjack.domain.vo;

import java.util.List;

public enum CardNumber {
    A(List.of(1, 11), "Ace"),
    TWO(List.of(2), "Two"),
    THREE(List.of(3), "Three"),
    FOUR(List.of(4), "Four"),
    FIVE(List.of(5), "Five"),
    SIX(List.of(6), "Six"),
    SEVEN(List.of(7), "Seven"),
    EIGHT(List.of(8), "Eight"),
    NINE(List.of(9), "Nine"),
    TEN(List.of(10), "Ten"),
    K(List.of(10), "King"),
    Q(List.of(10), "Queen"),
    J(List.of(10), "Jack");

    private final List<Integer> numbers;
    private final String description;

    CardNumber(List<Integer> numbers, String description) {
        this.numbers = numbers;
        this.description = description;
    }

    public List<Integer> numbers() {
        return numbers;
    }

    public String description() {
        return description;
    }
}