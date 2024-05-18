package io.github.gunkim.banking.application;

import io.github.gunkim.banking.domain.*;

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

        accountRepository.save(account);
        transactionRepository.save(Transaction.of(TransactionType.DEPOSIT, depositAmount, depositedBalanceAmount));
    }

    public void withdraw(AccountId accountId, Money withdrawAmount) {
        var account = findAccount(accountId);
        var withdrawnBalanceAmount = account.withdraw(withdrawAmount);

        accountRepository.save(account);
        transactionRepository.save(Transaction.of(TransactionType.WITHDRAW, withdrawAmount, withdrawnBalanceAmount));
    }

    private Account findAccount(AccountId accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("해당 account(%s)를 찾을 수 없습니다.".formatted(accountId)));
    }
}
