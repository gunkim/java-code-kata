package io.github.gunkim.banking.domain;

public interface TransactionRepository {
    void save(Transaction transaction);
}
