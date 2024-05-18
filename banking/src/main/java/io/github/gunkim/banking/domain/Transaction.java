package io.github.gunkim.banking.domain;

import java.time.LocalDateTime;

public record Transaction(
        TransactionId id,
        TransactionType type,
        Money amount,
        Money balance,
        LocalDateTime createdAt
) {
    public static Transaction of(TransactionType type, Money amount, Money balance) {
        return new Transaction(
                TransactionId.createRandom(),
                type,
                amount,
                balance,
                LocalDateTime.now()
        );
    }
}
