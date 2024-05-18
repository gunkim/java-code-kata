package io.github.gunkim.banking.domain;

public record Money(int value) {
    public Money {
        if (value < 0) {
            throw new IllegalArgumentException("금액은 음수가 될 수 없습니다.");
        }
    }

    public Money plus(Money money) {
        return new Money(this.value + money.value());
    }

    public Money minus(Money amount) {
        return new Money(this.value - amount.value());
    }
}
