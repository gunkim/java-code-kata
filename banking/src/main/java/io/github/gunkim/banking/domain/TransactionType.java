package io.github.gunkim.banking.domain;

public enum TransactionType {
    DEPOSIT("+"), WITHDRAW("-");

    private final String prefix;

    TransactionType(String prefix) {
        this.prefix = prefix;
    }

    public String prefix() {
        return prefix;
    }
}
