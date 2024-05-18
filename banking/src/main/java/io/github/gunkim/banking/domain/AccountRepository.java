package io.github.gunkim.banking.domain;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(AccountId id);

    void save(Account account);
}
