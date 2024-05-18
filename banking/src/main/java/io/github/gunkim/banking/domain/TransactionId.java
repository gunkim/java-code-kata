package io.github.gunkim.banking.domain;

import java.util.UUID;

public class TransactionId extends Identifier {
    protected TransactionId(UUID value) {
        super(value);
    }

    public static TransactionId createRandom() {
        return new TransactionId(UUID.randomUUID());
    }
}
