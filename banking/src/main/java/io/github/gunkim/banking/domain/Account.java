package io.github.gunkim.banking.domain;

public class Account {
    private final AccountId id;
    private Money balance;

    public Account(AccountId id, Money balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account(Money balance) {
        this(AccountId.createRandom(), balance);
    }

    public static Account zero(AccountId id) {
        return new Account(id, Money.ZERO);
    }

    public Money deposit(Money amount) {
        this.balance = this.balance.plus(amount);

        return this.balance;
    }

    public Money withdraw(Money amount) {
        if (amount.value() > this.balance.value()) {
            throw new IllegalArgumentException("현재 계좌 잔액(%d)보다 출금 금액(%d)이 더 큽니다.".formatted(this.balance.value(), amount.value()));
        }
        this.balance = this.balance.minus(amount);

        return this.balance;
    }

    public AccountId id() {
        return id;
    }

    public Money balance() {
        return this.balance;
    }
}
