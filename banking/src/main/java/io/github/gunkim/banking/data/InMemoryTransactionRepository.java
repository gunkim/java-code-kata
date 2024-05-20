package io.github.gunkim.banking.data;

import io.github.gunkim.banking.domain.AccountId;
import io.github.gunkim.banking.domain.Transaction;
import io.github.gunkim.banking.domain.TransactionId;
import io.github.gunkim.banking.domain.TransactionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<TransactionId, Transaction> datas = new HashMap<>();

    @Override
    public void save(Transaction transaction) {
        var transactionId = requireNonNull(transaction.id(), "transaction id must not be null");
        datas.put(transactionId, transaction);
    }

    @Override
    public List<Transaction> findAllByAccountIdOrderByCreatedAtDesc(AccountId accountId) {
        return datas.values().stream()
                .filter(transaction -> transaction.accountId().equals(accountId))
                .sorted()
                .toList();
    }
}
