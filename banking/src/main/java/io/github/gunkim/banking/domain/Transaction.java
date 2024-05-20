package io.github.gunkim.banking.domain;

import java.time.LocalDateTime;

public record Transaction(
        TransactionId id,
        AccountId accountId,
        TransactionType type,
        Money amount,
        Money balance,
        LocalDateTime createdAt
) implements Comparable<Transaction> {
    public static Transaction of(AccountId accountId, TransactionType type, Money amount, Money balance) {
        return new Transaction(
                TransactionId.createRandom(),
                accountId,
                type,
                amount,
                balance,
                LocalDateTime.now()
        );
    }

    public String signedAmount() {
        return type.prefix() + this.amount;
    }

    @Override
    public int compareTo(Transaction transaction) {
        return this.createdAt.compareTo(transaction.createdAt);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", type=" + type +
                ", amount=" + amount +
                ", balance=" + balance +
                ", createdAt=" + createdAt +
                '}';
    }
}
