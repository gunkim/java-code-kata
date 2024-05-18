package io.github.gunkim.banking.domain;

import java.util.UUID;

public class AccountId extends Identifier {
    private AccountId(UUID value) {
        super(value);
    }

    public static AccountId createRandom() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId fromString(String value) {
        return new AccountId(UUID.fromString(value));
    }
}
