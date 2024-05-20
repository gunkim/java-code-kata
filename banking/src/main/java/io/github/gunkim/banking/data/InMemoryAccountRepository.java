package io.github.gunkim.banking.data;

import io.github.gunkim.banking.domain.Account;
import io.github.gunkim.banking.domain.AccountId;
import io.github.gunkim.banking.domain.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<AccountId, Account> datas = new HashMap<>();

    @Override
    public Optional<Account> findById(AccountId id) {
        return Optional.ofNullable(datas.get(id));
    }

    @Override
    public void save(Account account) {
        var accountId = requireNonNull(account.id(), "account id must not be null");
        datas.put(accountId, account);
    }
}
