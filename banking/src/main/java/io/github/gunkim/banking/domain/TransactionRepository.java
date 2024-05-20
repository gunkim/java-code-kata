package io.github.gunkim.banking.domain;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);

    List<Transaction> findAllByAccountIdOrderByCreatedAtDesc(AccountId accountId);
}
