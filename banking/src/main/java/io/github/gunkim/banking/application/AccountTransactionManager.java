package io.github.gunkim.banking.application;

import io.github.gunkim.banking.domain.*;

import java.util.List;

public class AccountTransactionManager {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountTransactionManager(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(AccountId accountId, Money depositAmount) {
        var account = findAccount(accountId);
        var depositedBalanceAmount = account.deposit(depositAmount);

        persist(account, createTransaction(accountId, TransactionType.DEPOSIT, depositAmount, depositedBalanceAmount));
    }

    public void withdraw(AccountId accountId, Money withdrawAmount) {
        var account = findAccount(accountId);
        var withdrawnBalanceAmount = account.withdraw(withdrawAmount);

        persist(account, createTransaction(accountId, TransactionType.WITHDRAW, withdrawAmount, withdrawnBalanceAmount));
    }

    public List<Transaction> findAll(AccountId accountId) {
        return transactionRepository.findAllByAccountIdOrderByCreatedAtDesc(accountId);
    }

    private Transaction createTransaction(AccountId accountId, TransactionType type, Money withdrawAmount, Money withdrawnBalanceAmount) {
        return Transaction.of(accountId, type, withdrawAmount, withdrawnBalanceAmount);
    }

    private Account findAccount(AccountId accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 account(%s)를 찾을 수 없습니다.".formatted(accountId)));
    }

    private void persist(Account account, Transaction transaction) {
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }
}
